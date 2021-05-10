<template>
  <a-card class="personal">
    <a-form-model
      :model="personalInfo"
      :label-col="labelCol"
      :wrapper-col="wrapperCol"
      ref="ruleForm"
    >
      <a-avatar
        :size="75"
        icon="user"
        class="user-avatar"
        :src="personalInfo.avatar"
      />

      <a-form-model-item label="昵称" required ref="name" prop="name">
        <a-input
          v-model="personalInfo.name"
          @blur="
            () => {
              $refs.name.onFieldBlur();
            }
          "
        />
      </a-form-model-item>

      <a-form-model-item label="出生日期" required prop="birthday">
        <a-date-picker
          v-model="personalInfo.birthday"
          type="date"
          placeholder="Pick a date"
          style="width: 100%"
        />
      </a-form-model-item>

      <a-form-model-item required label="联系方式" prop="phone">
        <a-input v-model="personalInfo.phone" />
      </a-form-model-item>

      <a-form-model-item required label="个人邮箱" prop="email">
        <a-mentions
          placeholder="邮箱"
          :prefix="['@']"
          @search="onSearch"
          v-model="personalInfo.email"
        >
          <a-mentions-option
            v-for="value in MOCK_DATA[prefix] || []"
            :key="value"
            :value="value"
          >
            {{ value }}
          </a-mentions-option>
        </a-mentions>
      </a-form-model-item>

      <a-form-model-item label="个人介绍">
        <a-input v-model="personalInfo.introduce" type="textarea" />
      </a-form-model-item>

      <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
        <a-popconfirm
          title="确认修改?"
          ok-text="修改"
          cancel-text="取消"
          @confirm="onSubmit"
        >
          <a-button type="primary"> 保存 </a-button>
        </a-popconfirm>

        <a-popconfirm
          title="确认删除?"
          ok-text="删除"
          cancel-text="取消"
          @confirm="resetForm"
        >
          <a-button style="margin-left: 20px"> 取消 </a-button>
        </a-popconfirm>
      </a-form-model-item>
    </a-form-model>
  </a-card>
</template>
<style lang="less" scoped>
.personal {
  margin: 0 24px;
}
.ant-card-bordered {
  display: grid;
  justify-items: center;
}
.ant-form-item {
  right: -40px;
}
.user-avatar {
  // right: -15px;
  margin-bottom: 20px;
  background-color: #87d068;
}
</style>
<style lang="less" >
.personal {
  .ant-card-body {
    min-width: 500px;
    // width: 60%;
  }
  .ant-col {
    margin-bottom: 0;
    margin-right: 15px;
  }
  .ant-mentions > textarea {
    text-align: left;
  }
}
</style>
<script>
import { setCookie, getCookie } from "@/util/util";
const MOCK_DATA = {
  "@": ["afc163", "zombiej", "yesmeck"],
};
export default {
  data() {
    return {
      prefix: "@",
      MOCK_DATA,

      labelCol: { span: 4 },
      wrapperCol: { span: 14 },
      personalInfo: {
        avatar: "@/../static/avatar_2.jpg", // 默认头像
        account: "",
        name: "",
        birthday: undefined,
        phone: undefined,
        email: "",
        introduce: "",
      },
      oldInfo: {},
      rules: {
        name: [
          {
            required: true,
            message: "Please input Activity name",
            trigger: "blur",
          },
          {
            min: 2,
            max: 10,
            message: "Length should be 2 to 10",
            trigger: "blur",
          },
        ],
        phone: [
          { required: true, message: "Please input Phone", trigger: "blur" },
          // todo 判断位数
        ],
        mail: [
          { required: true, message: "Please input mail", trigger: "blur" },
        ],
        birthday: [
          { required: true, message: "Please pick a date", trigger: "change" },
        ],
        gender: [
          {
            required: true,
            message: "Please select activity resource",
            trigger: "change",
          },
        ],
      },
    };
  },
  methods: {
    onSearch(_, prefix) {
      console.log(_, prefix);
      this.prefix = prefix;
    },
    onSubmit() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          let temp = JSON.parse(JSON.stringify(this.personalInfo));
          temp.avatar = temp.avatar.split("/")[3];
          this.$axios
            .post("api/user/changeUser", temp)
            .then((res) => {
              this.getAccount();

              this.$message.success("成功修改个人信息!");
            })
            .catch((err) => {
              this.$message.error("修改个人信息失败");
            });
        } else {
          this.$message.error("错误的输入，未通过表单验证");
          return false;
        }
      });
    },
    resetForm() {
      this.personalInfo = JSON.parse(JSON.stringify(this.oldInfo));
    },
    getAccount() {
      if (!this.personalInfo.account) {
        let account = getCookie("account");

        this.$axios
          .get(`api/user/getUser?account=${account}`)
          .then((res) => {
            this.oldInfo = res.data;
            this.oldInfo.avatar = `@/../static/${this.oldInfo.avatar}`;
            this.personalInfo = JSON.parse(JSON.stringify(this.oldInfo));
          })
          .catch((err) => {
            console.log(err);
            this.$message.error("获取用户信息失败");
          });
      }
    },
  },
  mounted() {
    this.getAccount();
  },
};
</script>
