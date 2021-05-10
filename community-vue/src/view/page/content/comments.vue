<template>
  <a-card class="comments">
    <div class="comments-top">
      <a-button type="danger" @click="showDeleteConfirm"> 批量删除 </a-button>
      <a-input-search placeholder="input search text" style="width: 200px" />
    </div>

    <a-table
      :row-selection="{
        selectedRowKeys: selectedRowKeys,
        onChange: onSelectChange,
      }"
      :columns="columns"
      :data-source="data"
      :pagination="{ pageSize: 7 }"
    >
      <span slot="type" slot-scope="tag">
        <a-tag
          :key="tag"
          :color="tag === '评论' ? 'green' : tag === '文章' ? 'cyan' : 'blue'"
        >
          {{ tag }}
        </a-tag>
      </span>

      <!-- slot-scope="record" -->
      <span slot="action" slot-scope="text, record">
        <a-popconfirm
          title="是否确认删除该条评论?"
          ok-text="是"
          cancel-text="否"
          @confirm="deleteDate(record.id, record.oppositeSide)"
        >
          <a>删除</a>
        </a-popconfirm>
      </span>

      <p slot="expandedRowRender" slot-scope="record" style="margin: 0">
        <b>回复对象：</b>{{ record.oppositeSide }}
        <br />
        <b>回复时间：</b>{{ record.time }}
        <br />
        <b>回复内容：</b>{{ record.content }}
      </p>
    </a-table>
  </a-card>
</template>
<style lang="less" scoped>
.comments {
  margin: 0 24px;
  min-height: 600px;
  .comments-top {
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
    width: 60,
  },
  {
    title: "用户昵称",
    dataIndex: "name",
  },
  {
    title: "用户账号",
    dataIndex: "account",
  },
  {
    title: "回复对象",
    dataIndex: "oppositeSide",
    ellipsis: true,
  },
  {
    title: "回复内容",
    dataIndex: "content",
    ellipsis: true,
  },
  {
    title: "点赞数",
    dataIndex: "like",
    width: 100,
    sorter: (a, b) => a.like - b.like,
  },
  {
    title: "回复时间",
    dataIndex: "time",
    width: 180,
    sorter: (a, b) => new Date(a.time) - new Date(b.time),
  },
  {
    title: "回复类型",
    dataIndex: "type",
    scopedSlots: { customRender: "type" },
    filters: [
      {
        text: "评论",
        value: "评论",
      },
      {
        text: "文章",
        value: "文章",
      },
      {
        text: "话题",
        value: "话题",
      },
    ],
    // filterMultiple: false,
    onFilter: (value, record) => record.type.indexOf(value) != -1,
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
    };
  },
  computed: {
    hasSelected() {
      return this.selectedRowKeys.length > 0;
    },
  },
  methods: {
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
            // let temp = {id: _thi}
            res.push({
              id: _this.data[element - 1].id,
              oppositeSide: _this.data[element - 1].oppositeSide,
            });
          });
          res.forEach((element) => {
            _this.deleteDate(element.id, element.oppositeSide);
          });
          _this.selectedRowKeys = [];
        },
        onCancel() {
          console.log("Cancel");
        },
      });
    },
    getContent() {
      this.$axios
        .get(`api/func/getAllComments`)
        .then((res) => {
          this.data = res.data;
          console.log(this.data);
          for (let i = 0; i < this.data.length; i++) {
            this.data[i].key = i + 1;
          }
          this.originalList = JSON.parse(JSON.stringify(this.data));
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("获取评论内容失败");
        });
    },
    deleteDate(id, oppositeSide) {
      console.log(id);
      this.$axios
        .get(`api/func/removeComments?id=${id}&oppositeSide=${oppositeSide}`)
        .then((res) => {
          this.getContent();
          this.$message.success(`删除成功！`);
        })
        .catch((err) => {
          console.log(err);
          this.$message.error(`删除${id}失败`);
        });
    },
  },
  mounted() {
    this.getContent();
  },
};
</script>
