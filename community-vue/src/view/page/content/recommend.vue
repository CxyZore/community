<template>
  <a-card id="recommend">
    <a-row :gutter="[16, 8]">
      <a-col :span="8">
        <a-card
          title="特别推荐"
          :headStyle="headStyleList[0]"
          class="recommend-card"
        >
          <vuedraggable v-model="items">
            <div
              class="recommend-card-item"
              v-for="item in items"
              :key="item.key"
            >
              <div class="recommend-card-item-left">
                <p class="recommend-card-item-title">{{ item.title }}</p>
                <p class="recommend-card-item-author">{{ item.author }}</p>
              </div>
              <div class="recommend-card-item-right">
                <a slot="actions" @click="showDeleteConfirm">移除</a>
              </div>
            </div>
          </vuedraggable>
          <a-button type="primary" block> 新增 </a-button>
        </a-card>
      </a-col>
      <a-col :span="8"
        ><a-card title="文章推荐" :headStyle="headStyleList[1]">
          whatever content
        </a-card></a-col
      >
      <a-col :span="8"
        ><a-card title="话题推荐" :headStyle="headStyleList[2]">
          whatever content
        </a-card></a-col
      >
    </a-row>
  </a-card>
</template>
<style lang="less" scoped>
#recommend {
  margin: 0 24px;
  min-height: 600px;
  .ant-list-item-meta-content {
    text-align: left;
  }
  .recommend-card-item {
    height: 80px;
    padding: 0;
    padding-top: 15px;
    border: 0px;
    border-bottom: 1px solid #e8e8e8;
    line-height: 15px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .recommend-card-item-left {
      text-align: left;
      .recommend-card-item-title{
          color: rgba(0, 0, 0, 0.65)
      }
      .recommend-card-item-author{
          color: rgba(0, 0, 0, 0.45);
          padding-left:5px
      }
    }
    .recommend-card-item-right{
        position: relative;
        top:-10px;
        padding-right: 10px
    }
  }
}
</style>


<script>
import vuedraggable from "vuedraggable";
export default {
  components: {
    vuedraggable,
  },
  data() {
    return {
      items: [
        { key: 1, title: "【JVM】垃圾回收相关概念", author: "The author 1" },
        { key: 2, title: "【JVM】垃圾回收相关概念", author: "The author 2" },
        { key: 3, title: "【JVM】垃圾回收相关概念", author: "The author 3" },
        { key: 4, title: "【JVM】垃圾回收相关概念", author: "The author 4" },
        { key: 5, title: "【JVM】垃圾回收相关概念", author: "The author 5" },
      ],
      headStyleList: [
        {
          color: "white",
          background: "#f50",
        },
        {
          color: "white",
          background: "#2db7f5",
        },
        {
          color: "white",
          background: "#87d068",
        },
      ],
    };
  },
  methods: {
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