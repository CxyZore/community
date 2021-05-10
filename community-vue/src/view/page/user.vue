<template>
  <div id="user">
    <div class="user-top">
      <a-space size="middle">
        <a-button type="primary" @click="showAddConfirm('add')"> 新增</a-button>
        <a-button type="danger" @click="showDeleteConfirm"> 批量删除 </a-button>
      </a-space>

      <a-input-search placeholder="input search text" style="width: 200px" />
    </div>

    <a-table
      :row-selection="{
        selectedRowKeys: selectedRowKeys,
        onChange: onSelectChange,
      }"
      :columns="columns"
      :data-source="data"
      bordered
      :pagination="{ pageSize: 5 }"
    >
      <span slot="avatar" class="avatar_td" slot-scope="avatar_url">
        <img :src="avatar_url" height="50" width="50" />
      </span>

      <span slot="action" slot-scope="record">
        <a @click="showAddConfirm('edit', record.key - 1)">编辑</a>
        <a-divider type="vertical" />
        <a>删除</a>
      </span>
    </a-table>

    <a-modal
      :title="modelTitle"
      :visible="visible"
      :confirm-loading="confirmLoading"
      @ok="handleAddOk"
      @cancel="handleAddCancel"
      ok-text="确认"
      cancel-text="取消"
      id="user-add-modal"
    >
      <!-- @submit="handleSubmit" -->
      <a-form-model
        ref="ruleForm"
        :model="userForm"
        :rules="rules"
        :label-col="{ span: 7 }"
        :wrapper-col="{ span: 13 }"
      >
        <div class="user-avatar-upload">
          <a-upload
            name="avatar"
            list-type="picture-card"
            class="avatar-uploader"
            :show-upload-list="false"
            action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
            :before-upload="beforeUpload"
            @change="handleChange"
          >
            <!-- 头像 -->
            <img
              v-if="imageUrl"
              :src="imageUrl"
              style="width: 100%"
              alt="avatar"
            />
            <div v-else>
              <a-icon :type="avatarLoading ? 'loading' : 'plus'" />
              <div class="ant-upload-text">Upload</div>
            </div>
          </a-upload>
        </div>
        <!-- 昵称 -->
        <a-form-model-item label="昵称" prop="name">
          <a-input placeholder="name" v-model="userForm.name" />
        </a-form-model-item>
        <!-- 账号 -->
        <a-form-model-item label="账号" prop="account">
          <a-input placeholder="account" v-model="userForm.account" />
        </a-form-model-item>
        <!-- 电话 -->
        <a-form-model-item label="电话" prop="phone">
          <a-input-number
            id="inputNumber"
            v-model="userForm.phone"
            style="width: 100%"
          />
          <!-- @change="onChange" -->
        </a-form-model-item>
        <!-- 生日 -->
        <a-form-model-item label="生日">
          <a-date-picker v-model="userForm.birthday" style="width: 100%" />
        </a-form-model-item>
        <!-- 邮箱 -->
        <a-form-model-item label="邮箱" prop="mail">
          <a-auto-complete
            placeholder="input mail"
            @search="handleEmailSearch"
            v-model="userForm.email"
          >
            <template slot="dataSource">
              <a-select-option v-for="email in result" :key="email">
                {{ email }}
              </a-select-option>
            </template>
          </a-auto-complete>
        </a-form-model-item>

        <!-- 介绍 -->
        <a-form-model-item label="介绍">
          <a-input v-model="userForm.introduce" type="textarea" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>
<style lang="less" scoped>
#user {
  margin: 0 24px;
  padding: 30px;
  min-height: 600px;
  background: #fff;

  .user-top {
    display: flex;
    justify-content: space-between;
    margin-bottom: 15px;
  }

  .avatar-uploader > .ant-upload {
    width: 128px;
    height: 128px;
  }
  .ant-upload-select-picture-card i {
    font-size: 32px;
    color: #999;
  }

  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }
  .ant-upload-select-picture-card img {
    width: 128px;
    height: 128px;
  }

  #user-add-modal {
    display: flex;
    flex-direction: column;
    .ant-form {
      width: 75%;

      .ant-form-item {
        margin-bottom: 15px;
        .ant-form-item-label {
          padding-right: 10px;
        }
      }
    }
    .ant-upload-picture-card-wrapper {
      display: flex;
      justify-content: center;
      margin-bottom: 10px;
    }
  }
}
</style>
<style lang="less">
#user-add-modal {
  .ant-modal {
    top: 15px;
    padding-bottom: 0px;
    .user-avatar-upload {
      display: flex;
      justify-content: center;
    }
    .ant-upload-picture-card-wrapper {
      display: flex;
      justify-content: center;
      margin: 0 0 10px 10%;
    }
  }
}
</style>

<script>
const columns = [
  {
    title: "序号",
    dataIndex: "key",
    width: 70,
  },
  {
    title: "头像",
    dataIndex: "avatar",
    scopedSlots: { customRender: "avatar" },
    width: 100,
  },
  {
    title: "昵称",
    dataIndex: "name",
  },
  {
    title: "账号",
    dataIndex: "account",
  },

  {
    title: "联系方式",
    dataIndex: "phone",
  },
  {
    title: "邮箱",
    dataIndex: "email",
    width: 160,
  },
  {
    title: "生日",
    dataIndex: "birthday",
  },
  {
    title: "介绍",
    dataIndex: "introduce",
    ellipsis: true,
    width: 200,
  },
  {
    title: "操作",
    scopedSlots: { customRender: "action" },
  },
];

function getBase64(img, callback) {
  const reader = new FileReader();
  reader.addEventListener("load", () => callback(reader.result));
  reader.readAsDataURL(img);
}

const data = [];
for (let i = 0; i < 10; i++) {
  data.push({
    key: i + 1,
    avatar: `@/../static/avatar_${i % 5}.jpg`,
    name: `Edrward ${i}`,
    account: `account_${i}`,
    phone: "12345678910",
    email: `${i}email@email.com`,
    introduce: `hello world ， My name is John Brown, I am 32 years old, living in New York No. 1 Lake Park.`,
    birthday: `2020-10-27`,
  });
}
const rules = {
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
  account: [
    {
      required: true,
      message: "Please input Activity account",
      trigger: "blur",
    },
    {
      min: 5,
      max: 20,
      message: "Length should be 5 to 20",
      trigger: "blur",
    },
  ],
  phone: [
    { required: true, message: "Please input Phone", trigger: "blur" },
    // todo: 判断位数
  ],
  email: [{ required: true, message: "Please input mail", trigger: "blur" }],
};
export default {
  data() {
    this.cacheData = data.map((item) => ({ ...item }));
    return {
      data,
      columns,
      rules,
      editingKey: "",
      selectedRowKeys: [], // Check here to configure the default column
      visible: false,
      confirmLoading: false,
      avatarLoading: false,
      imageUrl: "",

      emailList: [],
      fields: ["name", "account", "phone", "email"],
      result: [],
      userForm: {
        name: "",
        account: "",
        phone: "",
        email: "",
        birthday: "",
        introduce: "",
      },
      modelTitle: "新增用户",
    };
  },
  methods: {
    onSelectChange(selectedRowKeys) {
      console.log("selectedRowKeys changed: ", selectedRowKeys);
      this.selectedRowKeys = selectedRowKeys;
    },
    showDeleteConfirm() {
      this.$confirm({
        title: "是否确认删除？",
        content: "此操作无法撤销",
        okText: "确认",
        okType: "danger",
        cancelText: "取消",
        onOk() {
          console.log("OK");
        },
        onCancel() {
          console.log("Cancel");
        },
      });
    },
    edit(key) {
      const newData = [...this.data];
      const target = newData.filter((item) => key === item.key)[0];
      this.editingKey = key;
      if (target) {
        target.editable = true;
        this.data = newData;
      }
    },
    cancel(key) {
      const newData = [...this.data];
      const target = newData.filter((item) => key === item.key)[0];
      this.editingKey = "";
      if (target) {
        Object.assign(
          target,
          this.cacheData.filter((item) => key === item.key)[0]
        );
        delete target.editable;
        this.data = newData;
      }
    },

    showAddConfirm(val, key) {
      if (val === "edit") {
        this.modelTitle = "编辑用户";
        this.userForm = this.data[key];
      } else if (val === "add") {
        this.modelTitle = "新增用户";
        this.userForm = {
          name: "",
          account: "",
          phone: "",
          email: "",
          birthday: "",
          introduce: "",
        };
      }
      this.visible = true;
    },
    handleAddOk(e) {
      let _this = this;

      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          alert("submit!");
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    handleAddCancel(e) {
      let _this = this;
      this.$confirm({
        title: "Are you sure delete this task?",
        content: "Some descriptions",
        okText: "Yes",
        okType: "danger",
        cancelText: "No",
        onOk() {
          console.log("OK");
          console.log("Clicked  add cancel button");
          _this.visible = false;
          _this.$refs.ruleForm.resetFields();
        },
        onCancel() {
          console.log("Cancel add");
          _this.$refs.ruleForm.resetFields();
        },
      });
    },
    handleChange(info) {
      if (info.file.status === "uploading") {
        this.avatarLoading = true;
        return;
      }
      if (info.file.status === "done") {
        // Get this url from response in real world.
        getBase64(info.file.originFileObj, (imageUrl) => {
          this.imageUrl = imageUrl;
          this.avatarLoading = false;
        });
      }
    },
    beforeUpload(file) {
      console.log(file.type);
      const isJpgOrPng =
        file.type === "image/jpeg" || file.type === "image/png";
      if (!isJpgOrPng) {
        this.$message.error("You can only upload JPG file!");
      }
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error("Image must smaller than 2MB!");
      }
      return isJpgOrPng && isLt2M;
    },
    handleEmailSearch(value) {
      let result;
      if (!value || value.indexOf("@") >= 0) {
        result = [];
      } else {
        result = ["gmail.com", "163.com", "qq.com"].map(
          (domain) => `${value}@${domain}`
        );
      }
      this.result = result;
    },
  },
};
</script>
