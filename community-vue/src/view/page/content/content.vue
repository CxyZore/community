<template>
  <a-card id="content">
    <a-tabs default-active-key="1" @change="callback">
      <a-tab-pane key="1" tab="文章管理">
        <div class="content_top">
          <a-popconfirm
            title="是否确认批量删除?"
            ok-text="是"
            cancel-text="否"
            @confirm="deleteAnyDate('文章')"
          >
            <a-button type="danger"> 批量删除 </a-button>
          </a-popconfirm>
          <a-input-search
            placeholder="input search text"
            style="width: 200px"
            v-model="search"
          />
        </div>
        <a-table
          :row-selection="{
            selectedRowKeys: selectedRowKeys,
            onChange: onSelectChange,
          }"
          :columns="columns"
          :data-source="data"
          :pagination="{ pageSize: 5 }"
          :scroll="{ x: 1300 }"
        >
          <span slot="label" slot-scope="label">
            <a-tag
              v-for="tag in label"
              :key="tag"
              :color="
                tag.length < 3
                  ? 'volcano'
                  : tag.length > 5
                  ? 'geekblue'
                  : 'green'
              "
            >
              {{ tag }}
            </a-tag>
          </span>

          <span slot="action" slot-scope="text, record">
            <a @click="showDrawer(record.key)">查看</a>
            <a-divider type="vertical" />
            <a-popconfirm
              title="是否确认删除该条信息?"
              ok-text="是"
              cancel-text="否"
              @confirm="deleteDate(record.type, record.id)"
            >
              <a>删除</a>
            </a-popconfirm>

            <a-divider type="vertical" />
            <a-switch
              checked-children="开"
              un-checked-children="锁"
              :checked="record.state"
              @change="onToggle(record.type,record.key)"
            />
            <!--  -->
            <!-- 
              @click="onToggle(record.type, record.key)"
              v-model="record.state" -->
          </span>
        </a-table>
      </a-tab-pane>
      <a-tab-pane key="2" tab="话题管理">
        <div class="content_top">
          <a-popconfirm title="是否确认批量删除?" ok-text="是" cancel-text="否">
            <a-button type="danger"> 批量删除 </a-button>
          </a-popconfirm>
          <a-input-search
            placeholder="input search text"
            style="width: 200px"
            v-model="topicSearch"
          />
        </div>
        <a-table
          :row-selection="{
            topicSelectedRowKeys: topicSelectedRowKeys,
            onChange: onTopicSelectChange,
          }"
          :columns="topicColumns"
          :data-source="topicData"
          :pagination="{ pageSize: 7 }"
        >
          <!-- slot-scope="text, record" -->
          <span slot="action" slot-scope="text, record">
            <a>删除</a>
            <a-divider type="vertical" />
            <a-switch
              checked-children="开"
              un-checked-children="锁"
              :checked="record.state"
              @change="onToggle(record.type,record.key)"
            />
          </span>
          <div slot="expandedRowRender" slot-scope="record">
            <p><strong>标题：</strong>{{ record.title }}</p>
            <p><strong>作者：</strong>{{ record.author }}</p>
            <p><strong>内容：</strong>{{ record.content }}</p>
          </div>
        </a-table>
      </a-tab-pane>
    </a-tabs>

    <a-drawer
      :title="drawerTitle"
      placement="right"
      width="640"
      :closable="false"
      :visible="visible"
      :after-visible-change="afterVisibleChange"
      @close="onClose"
    >
      <p>{{ drawerContent }}</p>
    </a-drawer>
  </a-card>
</template>

<style lang="less" scoped>
#content {
  margin: 0 24px;
  min-height: 600px;

  .content_top {
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
<style lang="less">
#content {
  .ant-tabs-nav-scroll {
    text-align: left;
  }
}
</style>

<script>
const columns = [
  {
    title: "序号",
    dataIndex: "key",
    key: "key",
    width: 60,
    fixed: "left",
  },
  {
    title: "标题",
    dataIndex: "title",
    key: "title",
    width: 180,
    ellipsis: true,
    fixed: "left",
  },
  {
    title: "作者",
    key: "author",
    dataIndex: "author",
    width: 120,
    ellipsis: true,
    fixed: "left",
  },
  {
    title: "内容",
    key: "content",
    dataIndex: "content",
    width: 200,
    ellipsis: true,
  },
  {
    title: "浏览量",
    key: "hot",
    dataIndex: "hot",
    width: 95,
    sorter: (a, b) => a.hot - b.hot,
  },
  {
    title: "点赞量",
    key: "like",
    dataIndex: "like",
    width: 95,
    sorter: (a, b) => a.like - b.like,
  },
  {
    title: "收藏数",
    key: "collect",
    dataIndex: "collect",
    width: 95,
    sorter: (a, b) => a.collect - b.collect,
  },
  {
    title: "发布时间",
    key: "time",
    dataIndex: "time",
    sorter: (a, b) => new Date(a.time) - new Date(b.time),
    width: 180,
  },
  {
    title: "标签",
    key: "label",
    dataIndex: "label",
    scopedSlots: { customRender: "label" },
    width: 200,
    filters: [
      {
        text: "前端开发",
        value: "前端开发",
      },
      {
        text: "后端开发",
        value: "后端开发",
      },
    ],
    onFilter: (value, record) => record.label.indexOf(value) != -1,
  },
  {
    title: "操作",
    key: "action",
    dataIndex: "action",
    scopedSlots: { customRender: "action" },
    width: 200,
    fixed: "right",
  },
];

const topicColumns = [
  {
    title: "序号",
    dataIndex: "key",
    key: "key",
    width: 60,
  },
  {
    title: "标题",
    dataIndex: "title",
    key: "title",
    ellipsis: true,
  },
  {
    title: "发布者",
    key: "author",
    dataIndex: "author",
    ellipsis: true,
  },
  {
    title: "内容",
    key: "content",
    dataIndex: "content",
    ellipsis: true,
  },
  {
    title: "浏览量",
    key: "hot",
    dataIndex: "hot",
    width: 95,
    sorter: (a, b) => a.hot - b.hot,
  },
  {
    title: "点赞量",
    key: "like",
    dataIndex: "like",
    width: 95,
    sorter: (a, b) => a.like - b.like,
  },
  {
    title: "发布时间",
    key: "time",
    dataIndex: "time",
    sorter: (a, b) => new Date(a.time) - new Date(b.time),
    width: 180,
  },
  {
    title: "标签",
    key: "label",
    dataIndex: "label",
    scopedSlots: { customRender: "label" },
    filters: [
      {
        text: "前端开发",
        value: "前端开发",
      },
      {
        text: "后端开发",
        value: "前端开发",
      },
    ],
    onFilter: (value, record) => record.label.indexOf(value) === 0,
  },
  {
    title: "操作",
    key: "action",
    dataIndex: "action",
    scopedSlots: { customRender: "action" },
  },
];

export default {
  data() {
    return {
      data: [],
      columns,

      topicData: [],
      topicColumns,

      visible: false,
      confirmLoading: false,

      selectedRowKeys: [],
      topicSelectedRowKeys: [],
      drawerTitle: "文章标题",
      drawerContent: "Some contents...",

      state: false,
      statu: true,

      search: "",
      topicSearch: "",
      originalList: {},
      wOriginalList: {},
    };
  },
  methods: {
    callback(key) {
      console.log(key);
    },
    afterVisibleChange(val) {
      console.log("visible", val);
    },
    showDrawer(key) {
      this.drawerTitle = this.data[key - 1].title;
      this.drawerContent = this.data[key - 1].content;
      this.visible = true;
    },
    onClose() {
      (this.drawerTitle = "文章标题"),
        (this.drawerContent = "Some contents...");
      this.visible = false;
    },

    onSelectChange(selectedRowKeys) {
      console.log("selectedRowKeys changed: ", selectedRowKeys);
      this.selectedRowKeys = selectedRowKeys;
    },
    onTopicSelectChange(topicSelectedRowKeys) {
      console.log("topicSelectedRowKeys changed: ", topicSelectedRowKeys);
      this.topicSelectedRowKeys = topicSelectedRowKeys;
    },
    handleOk(e) {
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
    getContent() {
      this.data = [];
      this.topicData = [];
      this.$axios
        .get(`api/content/getAllContent`)
        .then((res) => {
          let temp = res.data;
          console.log(temp);
          for (let key in temp) {
            if (temp.hasOwnProperty(key)) {
              let element = temp[key];
              element.label = element.label.split(",");

              let tempData;
              element.type === "文章"
                ? (tempData = this.data)
                : (tempData = this.topicData);

              element.state === "true"
                ? (element.state = true)
                : (element.state = false);

              tempData.push(element);
              tempData[tempData.length - 1].key = tempData.length;
            }
          }
          this.originalList = JSON.parse(JSON.stringify(this.topicData));
          this.wOriginalList = JSON.parse(JSON.stringify(this.data));
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("获取文章与话题信息失败");
        });
    },

    deleteDate(type, id) {
      this.$axios
        .get(`api/content/removeContent?id=${id}`)
        .then((res) => {
          this.$message.success(`删除${type}成功！`);
        })
        .catch((err) => {
          console.log(err);
          this.$message.error(`删除${type}失败`);
        });
    },
    deleteAnyDate(type) {
      let tempSelectedRowKeys;
      type === "文章"
        ? (tempSelectedRowKeys = this.selectedRowKeys)
        : (tempSelectedRowKeys = this.topicSelectedRowKeys);
      let tempData;
      type === "文章" ? (tempData = this.data) : (tempData = this.topicData);

      let res = [];
      tempSelectedRowKeys.forEach((element) => {
        res.push(tempData[element - 1].id);
      });
      console.log(res);
      this.$axios
        .post("api/content/removeContentArr", res)
        .then((res) => {
          this.getContent();
          this.tempSelectedRowKeys = [];
          this.$message.success("批量删除成功!");
        })
        .catch((err) => {
          this.$message.error("批量删除失败");
        });
    },
    onToggle(type, key) {
      let tempData = type === "文章" ? this.data : this.topicData;

      let temp = tempData[key - 1];
      temp.state = !temp.state;

      this.$axios
        .get(`api/content/changeContent?id=${temp.id}&state=${temp.state}`)
        .then((res) => {
          this.$message.success("修改内容状态成功");
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("修改内容状态失败");
        });
    },

  },
  watch: {
    topicSearch() {
      let dataList = this.topicData;
      let tempSearch = this.topicSearch;
      if (tempSearch.length > 0) {
        this.state = true;
        let list = [];
        dataList.map((msg) => {
          if (
            msg.author.indexOf(tempSearch) != -1 ||
            msg.title.indexOf(tempSearch) != -1 ||
            msg.content.indexOf(tempSearch) != -1
          ) {
            list.push(msg);
          }
        });
        console.log(list);
        this.topicData = list;
      } else {
        console.log(this.originalList);
        this.topicData = this.originalList;
      }
    },
    search() {
      let dataList = this.data;
      let tempSearch = this.search;
      if (tempSearch.length > 0) {
        let list = [];
        dataList.map((msg) => {
          if (
            msg.author.indexOf(tempSearch) != -1 ||
            msg.title.indexOf(tempSearch) != -1 ||
            msg.content.indexOf(tempSearch) != -1
          ) {
            list.push(msg);
          }
        });
        this.data = list;
      } else {
        this.data = this.wOriginalList;
      }
    },
  },
  mounted() {
    this.getContent();
  },
};
</script>
