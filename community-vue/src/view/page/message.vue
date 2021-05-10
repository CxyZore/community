<template>
  <a-card class="message">
    <div class="message-top">
      <a-button type="danger" @click="showDeleteConfirm"> 批量删除 </a-button>
      <!-- <a-popconfirm
            title="是否确认批量删除?"
            ok-text="是"
            cancel-text="否"
            @confirm="deleteAnyDate('文章')"
          >
            <a-button type="danger"> 批量删除 </a-button>
      </a-popconfirm> -->
      <a-input-search
        placeholder="input search text"
        style="width: 200px"
        v-model="msgSearch"
      />
    </div>

    <!-- todo: 修改筛选后页码问题  -->
    <a-table
      :row-selection="{
        selectedRowKeys: selectedRowKeys,
        onChange: onSelectChange,
      }"
      :columns="columns"
      :data-source="data"
      :pagination="{ pageSize: 7 }"
    >
      <span slot="tags" slot-scope="tag">
        <a-tag :key="tag" :color="tag === 'false' ? 'volcano' : 'green'">
          <span v-if="tag === 'true'">已回复</span>
          <span v-else>未回复</span>
        </a-tag>
      </span>

      <span slot="action" slot-scope="record">
        <a @click="showReplyModal(record.key - 1)">回复</a>
        <a-divider type="vertical" />
        <a-popconfirm
          title="是否确认删除该条留言?"
          ok-text="是"
          cancel-text="否"
          @confirm="deleteDate(record.id)"
        >
          <a>删除</a>
        </a-popconfirm>
        <a-divider type="vertical" />
      </span>

      <p slot="expandedRowRender" slot-scope="record" style="margin: 0">
        <b>留言：</b>{{ record.content }}
        <br />
        <b>回复：</b>{{ record.reply }}
      </p>
    </a-table>

    <a-modal
      title="留言回复"
      :visible="modalVisible"
      :confirm-loading="confirmLoading"
      ok-text="确认"
      cancel-text="取消"
      @ok="handleOk"
      @cancel="handleCancel"
    >
      <p>{{ userMessage }}</p>

      <a-textarea
        v-model="modalText"
        placeholder="reply content"
        :auto-size="{ minRows: 3, maxRows: 5 }"
      />
    </a-modal>
  </a-card>
</template>
<style lang="less" scoped>
.message {
  margin: 0 24px;
  min-height: 600px;
  .message-top {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-bottom: 15px;
    text-align: right;
  }
}
</style>
<script>
const columns = [
  {
    title: "序号",
    dataIndex: "key",
  },
  {
    title: "留言用户",
    dataIndex: "name",
  },
  {
    title: "留言时间",
    dataIndex: "time",
  },
  {
    title: "状态",
    dataIndex: "state",
    scopedSlots: { customRender: "tags" },
    filters: [
      {
        text: "true",
        value: "true",
      },
      {
        text: "false",
        value: "false",
      },
    ],
    filterMultiple: false,
    onFilter: (value, record) => record.state.indexOf(value) === 0,
  },
  {
    title: "操作",
    key: "action",
    scopedSlots: { customRender: "action" },
  },
];

export default {
  data() {
    return {
      data: [],
      columns,
      selectedRowKeys: [], // Check here to configure the default column
      loading: false,

      // modal参数
      userMessage: "用户留言内容",
      modalText: "",
      modalVisible: false,
      confirmLoading: false,
      modalId: "",

      msgSearch: "",
      originalList: [],
    };
  },
  computed: {
    hasSelected() {
      return this.selectedRowKeys.length > 0;
    },
  },
  methods: {
    start() {
      this.loading = true;
      setTimeout(() => {
        this.loading = false;
        this.selectedRowKeys = [];
      }, 1000);
    },
    onSelectChange(selectedRowKeys) {
      console.log("selectedRowKeys changed: ", selectedRowKeys);
      this.selectedRowKeys = selectedRowKeys;
    },
    showDeleteConfirm() {
      let _this = this;
      this.$confirm({
        title: "是否确认删除？",
        content: "此操作无法撤销",
        okText: "确认",
        okType: "danger",
        cancelText: "取消",
        onOk() {
          let res = [];
          _this.selectedRowKeys.forEach((element) => {
            res.push(_this.data[element - 1].id);
          });
          _this.$axios
            .post("api/func/removeMessageArr", res)
            .then((res) => {
              _this.getMessage();
              _this.selectedRowKeys = [];
              _this.$message.success("批量删除成功!");
            })
            .catch((err) => {
              _this.$message.error("批量删除失败");
            });
        },
        onCancel() {
          console.log("Cancel");
        },
      });
    },
    // modal操作
    showReplyModal(val) {
      this.modalId = val;
      this.userMessage = this.data[this.modalId].content;
      this.modalText = this.data[this.modalId].reply;
      this.modalVisible = true;
    },
    handleOk(e) {
      this.data[this.modalId].reply = this.modalText;
      this.confirmLoading = true;

      this.$axios
        .get(
          `api/func/reply?id=${this.data[this.modalId].id}&content=${
            this.modalText
          }`
        )
        .then((res) => {
          this.modalVisible = false;
          this.confirmLoading = false;
          this.getMessage();
          this.$message.success("回复成功");
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("回复失败");
        });
    },
    handleCancel(e) {
      console.log("Clicked cancel button");
      let _this = this;

      this.$confirm({
        title: "确定关闭当前窗口?",
        content: "所修改的记录将不被保存",
        okText: "确认",
        okType: "danger",
        cancelText: "取消",
        onOk() {
          console.log("OK");
          _this.modalText = "";
          _this.userMessage = "用户留言内容";
          _this.modalVisible = false;
        },
        onCancel() {
          console.log("Cancel");
        },
      });
    },
    getMessage() {
      this.$axios
        .get(`api/func/getAllMessage`)
        .then((res) => {
          this.data = res.data;
          for (let i = 0; i < this.data.length; i++) {
            this.data[i].key = i + 1;
            this.data[i].state = this.data[i].state === "1" ? "true" : "false";
          }
          this.originalList = JSON.parse(JSON.stringify(this.data));
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("获取留言内容失败");
        });
    },
    deleteDate(id) {
      this.$axios
        .get(`api/func/removeMessage?id=${id}`)
        .then((res) => {
          this.getMessage();
          this.$message.success(`删除成功！`);
        })
        .catch((err) => {
          console.log(err);
          this.$message.error(`删除失败`);
        });
    },
  },
  watch: {
    msgSearch() {
      let dataList = JSON.parse(JSON.stringify(this.originalList));
      let tempSearch = this.msgSearch;
      if (tempSearch.length > 0) {
        let list = [];
        dataList.map((msg) => {
          if (
            msg.name.indexOf(tempSearch) != -1 ||
            msg.content.indexOf(tempSearch) != -1 ||
            (msg.reply != null && msg.reply.indexOf(tempSearch) != -1)
          ) {
            list.push(msg);
          }
        });
        this.data = list;
      } else {
        this.data = JSON.parse(JSON.stringify(this.originalList));
      }
    },
  },
  mounted() {
    this.getMessage();
  },
};
</script>
