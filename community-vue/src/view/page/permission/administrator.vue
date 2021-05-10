<template>
  <div id="administrator">
    <div class="administrator-top">
      <a-space size="middle">
        <a-button type="primary" @click="showAddConfirm"> 新增</a-button>
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

      <template slot="name" slot-scope="text, record">
        <editable-cell
          :ref="`name_${record.key}`"
          :defaultEditable="record.editable"
          :autoSave="true"
          :required="true"
          :decoratorOptions="rules.name"
          placeholder="请输入"
          :value="text"
          :text="text"
          @valueChange="onCellChange(record.key, 'name', $event)"
        />
      </template>

      <template slot="role" slot-scope="text, record">
        <editable-cell
          :ref="`role_${record.key}`"
          formType="select"
          :defaultEditable="record.editable"
          :required="true"
          message="请选择角色"
          :value="record.role"
          :text="record.role"
        >
          <a-select-option value="role_0"> 超级管理员 </a-select-option>
          <a-select-option value="role_1"> 管理员 </a-select-option>
          <a-select-option value="role_2"> 游客 </a-select-option>
        </editable-cell>
      </template>

      <template slot="phone" slot-scope="text, record">
        <editable-cell
          :ref="`phone_${record.key}`"
          decoratorType="integer"
          formType="number"
          :defaultEditable="record.editable"
          :decoratorOptions="rules.age"
          :required="true"
          :min="11"
          :max="11"
          :value="text"
          :text="text"
          placeholder="请输入"
        />
      </template>

      <template slot="email" slot-scope="text, record">
        <editable-cell
          :ref="`email_${record.key}`"
          :defaultEditable="record.editable"
          :required="true"
          formType="autoComplete"
          :decoratorOptions="rules.email"
          placeholder="请输入"
          :value="text"
          :text="text"
          @autoCompleteSearch="handleSearch($event, record.key)"
        >
          <a-select-option
            v-for="email in emailList"
            :key="email"
            :text="email"
            :value="email"
          >
            {{ email }}
          </a-select-option>
        </editable-cell>
      </template>

      <template slot="operation" slot-scope="text, record">
        <div class="editable-row-operations">
          <span v-if="record.editable">
            <a @click="() => save(record.key)">Save</a>
            <a-popconfirm
              title="Sure to cancel?"
              @confirm="() => cancel(record.key)"
            >
              <a>Cancel</a>
            </a-popconfirm>
          </span>
          <span v-else>
            <a :disabled="editingKey !== ''" @click="() => edit(record.key)"
              >编辑</a
            ><a-divider type="vertical" />
            <a-popconfirm
              title="Sure to cancel?"
              @confirm="() => cancel(record.key)"
            >
              <a>删除</a>
            </a-popconfirm>
          </span>
        </div>
      </template>
    </a-table>

    <a-modal
      title="新增管理员"
      :visible="visible"
      :confirm-loading="confirmLoading"
      @ok="handleAddOk"
      @cancel="handleAddCancel"
      ok-text="确认"
      cancel-text="取消"
      id="admin-add-modal"
    >
      <!-- @submit="handleSubmit" -->
      <a-form-model
      
        ref="ruleForm"
        :model="adminForm"
        :rules="rules"
        :label-col="{ span: 7 }"
        :wrapper-col="{ span: 13 }"
      >
        <div class="admin-avatar-upload">
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
          <a-input placeholder="name" v-model="adminForm.name" />
        </a-form-model-item>
        <!-- 账号 -->
        <a-form-model-item label="账号" prop="account">
          <a-input placeholder="account" v-model="adminForm.account" />
        </a-form-model-item>
        <!-- 密码 -->
        <a-form-model-item label="密码" prop="password">
          <a-input-password
            placeholder="input password"
            v-model="adminForm.password"
          />
        </a-form-model-item>
        <!-- 确认密码 -->
        <a-form-model-item label="确认" prop="confirmPassword">
          <a-input-password
            placeholder="confirm password"
            v-model="adminForm.confirmPassword"
          />
        </a-form-model-item>
        <!-- 角色 -->
        <a-form-model-item label="角色" prop="role">
          <a-select
            default-value="lucy"
            @change="handleChange"
            v-model="adminForm.role"
          >
            <a-select-option value="jack"> Jack </a-select-option>
            <a-select-option value="lucy"> Lucy </a-select-option>
            <a-select-option value="disabled" disabled>
              Disabled
            </a-select-option>
            <a-select-option value="Yiminghe"> yiminghe </a-select-option>
          </a-select>
        </a-form-model-item>
        <!-- 电话 -->
        <a-form-model-item label="电话" prop="phone">
          <a-input-number
            id="inputNumber"
            v-model="adminForm.phone"
            style="width: 100%"
          />

          <!-- @change="onChange" -->
        </a-form-model-item>
        <!-- 邮箱 -->
        <a-form-model-item label="邮箱" prop="mail">
          <a-auto-complete
            placeholder="input mail"
            @search="handleEmailSearch"
            v-model="adminForm.mail"
          >
            <template slot="dataSource">
              <a-select-option v-for="email in result" :key="email">
                {{ email }}
              </a-select-option>
            </template>
          </a-auto-complete>
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </div>
</template>
<style lang="less" scoped>
#administrator {
  margin: 0 24px;
  padding: 30px;
  min-height: 600px;
  background: #fff;

  .administrator-top {
    display: flex;
    justify-content: space-between;
    margin-bottom: 15px;
  }

  .editable-row-operations a {
    margin-right: 8px;
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

  #admin-add-modal {
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
#admin-add-modal {
  .ant-modal {
    top: 15px;
    padding-bottom: 0px;
    .admin-avatar-upload {
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
import EditableCell from "@/components/EditableCell/cell.vue";
const columns = [
  {
    title: "key",
    dataIndex: "key",
  },
  {
    title: "avatar",
    dataIndex: "avatar",
    scopedSlots: { customRender: "avatar" },
  },
  {
    title: "name",
    dataIndex: "name",
    scopedSlots: { customRender: "name" },
  },
  {
    title: "account",
    dataIndex: "account",
  },
  {
    title: "role",
    dataIndex: "role",
    scopedSlots: { customRender: "role" },
    filters: [
      {
        text: "role_0",
        value: "role_0",
      },
      {
        text: "role_1",
        value: "role_1",
      },
      {
        text: "role_2",
        value: "role_2",
      },
    ],
    filterMultiple: false,
    onFilter: (value, record) => record.role.indexOf(value) === 0,
  },
  {
    title: "phone",
    dataIndex: "phone",
    scopedSlots: { customRender: "phone" },
  },
  {
    title: "email",
    dataIndex: "email",
    scopedSlots: { customRender: "email" },
  },
  {
    title: "operation",
    scopedSlots: { customRender: "operation" },
  },
];
const data = [];
function getBase64(img, callback) {
  const reader = new FileReader();
  reader.addEventListener("load", () => callback(reader.result));
  reader.readAsDataURL(img);
}
for (let i = 0; i < 10; i++) {
  data.push({
    key: i + 1,
    avatar: `@/../static/avatar_${i % 5}.jpg`,
    name: `Edrward ${i}`,
    account: `account_${i}`,
    role: `role_${i % 3}`,
    phone: "12345678910",
    email: `${i}@email.com`,
  });
}
export default {
  components: {
    EditableCell,
  },
  data() {
    this.cacheData = data.map((item) => ({ ...item }));
    return {
      data,
      columns,
      editingKey: "",
      selectedRowKeys: [], // Check here to configure the default column
      visible: false,
      confirmLoading: false,
      avatarLoading: false,
      imageUrl: "",

      emailList: [],
      fields: ["name", "phone", "role", "email"],
      result: [],
      adminForm: {
        name: "",
        account:'',
        password: "",
        confirmPassword: "",
        role: "jack",
        phone: "",
        mail: "",
      },

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
        password: [
          {
            required: true,
            message: "Please input your password!",
          },
          // {
          //   validator: validateToNextPassword,
          // },
        ],
        confirmPassword: [
          {
            required: true,
            message: "Please confirm your password!",
          },
          // {
          //   validator: compareToFirstPassword,
          // },
        ],
        phone: [
          { required: true, message: "Please input Phone", trigger: "blur" },
          // todo: 判断位数
        ],
        mail: [
          { required: true, message: "Please input mail", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    onSelectChange(selectedRowKeys) {
      console.log("selectedRowKeys changed: ", selectedRowKeys);
      this.selectedRowKeys = selectedRowKeys;
    },

    // 模糊搜索
    handleSearch(value) {
      let result;
      if (!value || value.indexOf("@") >= 0) {
        result = [];
      } else {
        result = ["gmail.com", "163.com", "qq.com"].map(
          (domain) => `${value}@${domain}`
        );
      }
      this.emailList = result;
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
    // input Change事件
    onCellChange(key, dataIndex, value) {
      console.log(`${dataIndex}：${value} `);
      const dataSource = [...this.data];
      const target = dataSource.find((item) => item.key === key);
      if (target) {
        target[dataIndex] = value;
        this.data = dataSource;
      }
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
    // 删除
    onDelete(key) {
      // 拷贝数组数据
      const dataSource = [...this.dataSource];
      // 过滤移除当前项目
      this.dataSource = dataSource.filter((item) => item.key !== key);
    },
    save(key) {
      this.validateRow(key, this.fields, (err, values) => {
        if (!err) {
          const newData = [...this.data];
          const row = newData.find((item) => key === item.key);
          if (row) {
            // 用户数据合并到原对象上
            Object.assign(row, values);
            delete row.editable;
            this.signData = row;
            console.log("单行保存row", row);
            // target 保存了最新行数据，可以提交到后台
            this.data = newData;
          }
          this.editingKey = "";
        }
      });
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
    // validateRow可以提取为公共函数
    // cxy - err: fields == undefined
    validateRow(key, fields, callback) {
      // 查找验证是否通过
      const row = {};
      let err = false;
      console.log(fields);
      // forEach 对每一个验证，可以更改为some满足一个就提醒
      fields.forEach((filed) => {
        let refName = `${filed}_${key}`;
        let result = this.$refs[refName].validate();
        row[filed] = result.value;
        if (!result.success) {
          err = true;
        }
      });
      callback(err, row);
    },
    // 点击提交
    handleAdd() {
      // todo
    },
    showAddConfirm() {
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

      // this.$confirm({
      //   title: "Do you Want to delete these items?",
      //   content: (h) => <div style="color:red;">Some descriptions</div>,
      //   onOk() {
      //     console.log("OK");
      //     _this.confirmLoading = true;
      //     setTimeout(() => {
      //       _this.visible = false;
      //       _this.confirmLoading = false;
      //     }, 2000);
      //   },
      //   onCancel() {
      //     console.log("Cancel");
      //   },
      //   class: "test",
      // });
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
