<template>
  <a-card id="role">
    <div class="role_top">
      <a-button type="primary" @click="showModal('add')">
      新增
    </a-button>
    </div>

    <a-table
      :columns="columns"
      :data-source="data"
      :pagination="{ pageSize: 7 }"
    >
      <a slot="name" slot-scope="text">{{ text }}</a>
      <span slot="customTitle"><a-icon type="smile-o" /> 角色</span>

      <span slot="state" slot-scope="state">
        <a-tag :key="state" :color="!state ? 'volcano' : 'green'">
          <span v-if="!state">停用</span>
          <span v-else>启用</span>
        </a-tag>
      </span>

      <!--  slot-scope="text, record" -->
      <span slot="action" slot-scope="text, record">
        <!-- <a>{{ record.key }}</a> -->
        <a @click="showModal(record.key)">编辑</a>
        <a-divider type="vertical" />
        <a @click="showDeleteConfirm">删除</a>
      </span>
    </a-table>

    <!-- :confirm-loading="confirmLoading"
      @ok="handleOk"
      @cancel="handleCancel" -->

    <a-modal
      title="角色编辑"
      :visible="visible"
      :confirm-loading="confirmLoading"
      @ok="handleOk"
      @cancel="handleCancel"
      ok-text="确认"
      cancel-text="取消"
    >
      <a-form-model
        ref="ruleForm"
        :model="form"
        :rules="rules"
        :label-col="{ span: 4 }"
        :wrapper-col="{ span: 14 }"
      >
        <a-form-model-item ref="name" label="角色名称" prop="name">
          <a-input
            v-model="form.name"
            @blur="
              () => {
                $refs.name.onFieldBlur();
              }
            "
          />
        </a-form-model-item>

        <a-form-model-item label="角色介绍" prop="introduce">
          <a-input v-model="form.introduce" type="textarea" />
        </a-form-model-item>

        <a-form-model-item label="创建时间">
          <a-input v-model="form.time" disabled />
        </a-form-model-item>

        <a-form-model-item label="启用状态" prop="state">
          <a-switch v-model="form.state" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </a-card>
</template>

<style lang="less" scoped>
#role {
  margin: 0 24px;
  min-height: 600px;
  .role_top {
    // float: left;
    display: flex;
    margin-bottom: 15px;
  }
  .ant-form-item {
    display: flex;
    justify-content: center;
  }
}
</style>

<script>
const columns = [
  {
    title: "序号",
    dataIndex: "key",
    key: "key",
  },
  {
    dataIndex: "name",
    key: "name",
    slots: { title: "customTitle" },
    scopedSlots: { customRender: "name" },
  },
  {
    title: "角色介绍",
    key: "introduce",
    dataIndex: "introduce",
  },
  {
    title: "创建时间",
    key: "time",
    dataIndex: "time",
    scopedSlots: { customRender: "time" },
  },
  {
    title: "状态",
    key: "state",
    dataIndex: "state",
    scopedSlots: { customRender: "state" },
  },
  {
    title: "操作",
    key: "action",
    dataIndex: "action",
    scopedSlots: { customRender: "action" },
  },
];

const data = [
  {
    key: 1,
    name: "super",
    introduce: "超级管理员，拥有所有权限",
    time: "2020-10-16 22:56:26",
    state: true,
  },
  {
    key: 2,
    name: "admin",
    introduce: "管理员，拥有部分权限",
    time: "2020-10-16 22:56:26",
    state: true,
  },
  {
    key: 3,
    name: "tourists",
    introduce: "游客，只能浏览",
    time: "2020-10-16 22:56:26",
    state: false,
  },
];

export default {
  data() {
    return {
      data,
      columns,

      ModalText: "Content of the modal",
      visible: false,
      confirmLoading: false,

      form: {
        name: "",
        introduce: "",
        time: "",
        state: true,
      },

      rules: {
        name: [
          { required: true, message: "请输入角色名", trigger: "blur" },
          { min: 2, max: 8, message: "角色名长度为2-8", trigger: "blur" },
        ],
        introduce: [
          { required: true, message: "请输入角色介绍", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    showModal(val) {
      if (val==='add') {
        this.form = {
          name: "",
          introduce: "",
          time: "",
          state: true,
        };
      } else {
        this.form = JSON.parse(JSON.stringify(this.data[val - 1]));
        // this.form = this.data[val - 1];
      }
      this.visible = true;
    },
    handleOk(e) {
      this.ModalText = "The modal will be closed after two seconds";
      this.confirmLoading = true;
      setTimeout(() => {
        this.visible = false;
        this.confirmLoading = false;
      }, 1000);
      this.$refs.ruleForm.resetFields(); // 重置表单
    },
    handleCancel(e) {
      console.log("Clicked cancel button");
      this.visible = false;
      this.$refs.ruleForm.resetFields(); // 重置表单
    },
    showDeleteConfirm() {
      this.$confirm({
        title: "Are you sure delete this task?",
        content: "Some descriptions",
        okText: "Yes",
        okType: "danger",
        cancelText: "No",
        onOk() {
          console.log("OK");
        },
        onCancel() {
          console.log("Cancel");
        },
      });
    },
  },
};
</script>