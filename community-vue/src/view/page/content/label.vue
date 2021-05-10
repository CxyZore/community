<template>
  <a-card id="label">
    <div class="label_top">
      <a-space size="middle">
        <a-button type="primary" @click="showModal('add')"> 新增</a-button>
        <a-button type="danger" @click="showDeleteConfirm"> 批量删除 </a-button>
      </a-space>

      <a-input-search placeholder="input search text" style="width: 200px" />
    </div>

    <a-table
    :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
      :columns="columns"
      :data-source="data"
      :pagination="{ pageSize: 7 }"
    >
      <span slot="type" slot-scope="type">
        <a-tag
          :key="type"
          :color="type === '文章' ? 'blue' : 'purple'"
        >
          <span v-if="type === '文章'">文章</span>
          <span v-else>话题</span>
        </a-tag>
      </span>

      <span slot="state" slot-scope="state">
        <a-tag :key="state" :color="state === 'false' ? '#f50' : '#87d068'">
          <span v-if="state === 'false'">停用</span>
          <span v-else>启用</span>
        </a-tag>
      </span>

      <span slot="action" slot-scope="text, record">
        <a @click="toTop(record.key)">置顶</a>
        <a-divider type="vertical" />
        <a @click="showModal(record.key)">编辑</a>
        <a-divider type="vertical" />
        <a @click="showDeleteConfirm">删除</a>
      </span>
    </a-table>

    <a-modal
      :title="modalTitle"
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
        :label-col="{ span: 7 }"
        :wrapper-col="{ span: 14 }"
      >
        <a-form-model-item label="标签名称">
          <a-input v-model="form.name" />
        </a-form-model-item>

        <a-form-model-item label="标签介绍">
          <a-input v-model="form.introduce" />
        </a-form-model-item>

        <a-form-model-item label="标签排序">
          <a-input-number v-model="form.sort" />
        </a-form-model-item>

        <a-form-model-item label="标签分类">
          <a-select v-model="form.type" placeholder="请选择该标签的分类">
            <a-select-option value="article"> 文章 </a-select-option>
            <a-select-option value="topic"> 话题 </a-select-option>
          </a-select>
        </a-form-model-item>

        <a-form-model-item label="启用状态" >
          <a-switch :checked="form.state === 'true'" @change="modelSwitchChange" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </a-card>
</template>

<style lang="less" scoped>
#label {
  margin: 0 24px;
  min-height: 600px;
  .label_top {
    // float: left;
    display: flex;
    justify-content: space-between;
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
    title: "标签名",
    dataIndex: "name",
    key: "name",
  },
  {
    title: "标签介绍",
    key: "introduce",
    dataIndex: "introduce",
  },
  {
    title: "点击数",
    key: "hot",
    dataIndex: "hot",
    sorter: (a, b) => a.hot - b.hot,
  },
  {
    title: "排序",
    key: "sort",
    dataIndex: "sort",
    sorter: (a, b) => a.sort - b.sort,
  },
  {
    title: "创建时间",
    key: "time",
    dataIndex: "time",
    sorter: (a, b) => new Date(a.time) - new Date(b.time),
  },
  {
    title: "分类",
    key: "type",
    dataIndex: "type",
    scopedSlots: { customRender: "type" },
    filters: [
      {
        text: '文章',
        value: '文章',
      },
      {
        text: '话题',
        value: '话题',
      },
    ],
    filterMultiple: false,
    onFilter: (value, record) => record.type.indexOf(value) === 0,
  },
  {
    title: "状态",
    key: "state",
    dataIndex: "state",
    scopedSlots: { customRender: "state" },
    filters: [
      {
        text: '启动',
        value: 'true',
      },
      {
        text: '停用',
        value: 'false',
      },
    ],
    filterMultiple: false,
    onFilter: (value, record) => record.state.indexOf(value) === 0
  },

  {
    title: "操作",
    key: "action",
    dataIndex: "action",
    scopedSlots: { customRender: "action" },
  },
];

//  

export default {
  data() {
    return {
      data : [],
      columns,

      visible: false,
      confirmLoading: false,

      selectedRowKeys: [],
      modalTitle: "对话框",

      form: {
        name: "",
        introduce: "",
        sort: "",
        type: "文章",
        state: true,
      },

      rules: {
        name: [
          { required: true, message: "请输入标签名称", trigger: "blur" },
          { min: 2, max: 5, message: "标签名长度为2-5", trigger: "blur" },
        ],
        sort: [{ required: true, message: "请输入排序", trigger: "blur" }],
      },
    };
  },
  methods: {
    toTop(key){
      // console.log(key)
      // console.log(this.data[key-1])
      let temp = this.data.splice(key-1,1);

      // console.log(temp);
      this.data.unshift(temp[0]);
    },
    modelSwitchChange(){
      this.form.state = this.form.state === 'true' ? 'false' :'true';
    },
    showModal(val) {
      if (val === "add") {
        this.modalTitle = "新增标签";
        this.form = {
          name: "",
          introduce: "",
          sort: "",
          type: "文章",
          state: true,
        };
      } else {
        this.modalTitle = "编辑标签";
        this.form = JSON.parse(JSON.stringify(this.data[val - 1]));
      }
      this.visible = true;
    },
    onSelectChange(selectedRowKeys) {
      console.log('selectedRowKeys changed: ', selectedRowKeys);
      this.selectedRowKeys = selectedRowKeys;
    },
    handleOk(e) {
      this.confirmLoading = true;
      let date = new Date();
      let time = `${date.getFullYear()}-${date.getMonth()}-${date.getDay()} ${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`
      if(this.modalTitle === '新增标签'){
        this.form.hot = 0;
        this.form.time =time;
        this.form.top = 0;
        this.form.id = 'l_'+time

        this.$axios
        .post("api/content/addLabel", this.form)
        .then((res) => {
          this.getLabel();
          this.confirmLoading = false;
          this.visible = false;
          this.$message.success("新增标签成功!");
        })
        .catch((err) => {
          this.confirmLoading = false;
          this.$message.error("新增标签失败");
        });
      }else{
        // console.log(typeof this.form.top)
        this.$axios
        .post("api/content/changeLabel", this.form)
        .then((res) => {
          this.getLabel();
          this.confirmLoading = false;
        this.visible = false;
          this.$message.success("修改标签成功!");
        })
        .catch((err) => {
          this.confirmLoading = false;
          this.$message.error("修改标签失败");
        });
      }
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
    getLabel(){
     let _this = this;
     this.$axios
        .get(`api/content/getAllLabel`)
        .then((res) => {
          // console.log(res.data)
          _this.data = res.data;
          for (let i = 0; i < _this.data.length; i++) {
            _this.data[i].key = i+1;
          }
          console.log(_this.data)
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("获取标签内容失败");
        });
   } 
  },
  mounted() {
    this.getLabel();
  }
};

</script>