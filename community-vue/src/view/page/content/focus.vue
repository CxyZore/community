<template>
  <a-card id="focus">
    <a-tabs default-active-key="1" @change="callback">
      <a-tab-pane key="1" tab="关注管理">
        <div class="focus_top">
          <a-button type="danger"> 批量删除 </a-button>
        </div>
        <a-table
          :data-source="data"
          :columns="columns"
          :pagination="{ pageSize: 7 }"
          :row-selection="{
            selectedRowKeys: selectedRowKeys,
            onChange: onSelectChange,
          }"
        >
          <div
            slot="filterDropdown"
            slot-scope="{
              setSelectedKeys,
              selectedKeys,
              confirm,
              clearFilters,
              column,
            }"
            style="padding: 8px"
          >
            <a-input
              v-ant-ref="(c) => (searchInput = c)"
              :placeholder="`Search ${column.dataIndex}`"
              :value="selectedKeys[0]"
              style="width: 188px; margin-bottom: 8px; display: block"
              @change="
                (e) => setSelectedKeys(e.target.value ? [e.target.value] : [])
              "
              @pressEnter="
                () => handleSearch(selectedKeys, confirm, column.dataIndex)
              "
            />
            <a-button
              type="primary"
              icon="search"
              size="small"
              style="width: 90px; margin-right: 8px"
              @click="
                () => handleSearch(selectedKeys, confirm, column.dataIndex)
              "
            >
              Search
            </a-button>
            <a-button
              size="small"
              style="width: 90px"
              @click="() => handleReset(clearFilters)"
            >
              Reset
            </a-button>
          </div>
          <a-icon
            slot="filterIcon"
            slot-scope="filtered"
            type="search"
            :style="{ color: filtered ? '#108ee9' : undefined }"
          />
          <template
            slot="customRender"
            slot-scope="text, record, index, column"
          >
            <span v-if="searchText && searchedColumn === column.dataIndex">
              <template
                v-for="(fragment, i) in text
                  .toString()
                  .split(
                    new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i')
                  )"
              >
                <mark
                  v-if="fragment.toLowerCase() === searchText.toLowerCase()"
                  :key="i"
                  class="highlight"
                  >{{ fragment }}</mark
                >
                <template v-else>{{ fragment }}</template>
              </template>
            </span>
            <template v-else>
              {{ text }}
            </template>
          </template>

          <span slot="action" slot-scope="text, record">
            
            <a-popconfirm
              title="是否确认删除该条关注?"
              ok-text="是"
              cancel-text="否"
              @confirm="delAttrntion(record.account, record.otherAccount)"
            >
              <a>删除</a>
            </a-popconfirm>
          </span>
        </a-table>
      </a-tab-pane>

      <a-tab-pane key="2" tab="收藏管理">
        <div class="focus_top">
          <a-button type="danger"> 批量删除 </a-button>
        </div>
        <a-table
          :data-source="collectData"
          :columns="collectColumns"
          :pagination="{ pageSize: 7 }"
          :row-selection="{
            selectedRowKeys: selectedRowKeys,
            onChange: onSelectChange,
          }"
        >
          <div
            slot="filterDropdown"
            slot-scope="{
              setSelectedKeys,
              selectedKeys,
              confirm,
              clearFilters,
              column,
            }"
            style="padding: 8px"
          >
            <a-input
              v-ant-ref="(c) => (searchInput = c)"
              :placeholder="`Search ${column.dataIndex}`"
              :value="selectedKeys[0]"
              style="width: 188px; margin-bottom: 8px; display: block"
              @change="
                (e) => setSelectedKeys(e.target.value ? [e.target.value] : [])
              "
              @pressEnter="
                () => handleSearch(selectedKeys, confirm, column.dataIndex)
              "
            />
            <a-button
              type="primary"
              icon="search"
              size="small"
              style="width: 90px; margin-right: 8px"
              @click="
                () => handleSearch(selectedKeys, confirm, column.dataIndex)
              "
            >
              Search
            </a-button>
            <a-button
              size="small"
              style="width: 90px"
              @click="() => handleReset(clearFilters)"
            >
              Reset
            </a-button>
          </div>
          <a-icon
            slot="filterIcon"
            slot-scope="filtered"
            type="search"
            :style="{ color: filtered ? '#108ee9' : undefined }"
          />
          <template
            slot="customRender"
            slot-scope="text, record, index, column"
          >
            <span v-if="searchText && searchedColumn === column.dataIndex">
              <template
                v-for="(fragment, i) in text
                  .toString()
                  .split(
                    new RegExp(`(?<=${searchText})|(?=${searchText})`, 'i')
                  )"
              >
                <mark
                  v-if="fragment.toLowerCase() === searchText.toLowerCase()"
                  :key="i"
                  class="highlight"
                  >{{ fragment }}</mark
                >
                <template v-else>{{ fragment }}</template>
              </template>
            </span>
            <template v-else>
              {{ text }}
            </template>
          </template>

          <span slot="action" slot-scope="text, record">
            
            <a-popconfirm
              title="是否确认删除该条收藏?"
              ok-text="是"
              cancel-text="否"
              @confirm="delCollect(record.account, record.articID)"
            >
              <a>删除</a>
            </a-popconfirm>
          </span>
        </a-table>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<style lang="less" scoped>
#focus {
  margin: 0 24px;
  min-height: 600px;

  .focus_top {
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
#focus {
  .ant-tabs-nav-scroll {
    text-align: left;
  }
  .highlight {
    background-color: rgb(255, 192, 105);
    padding: 0px;
  }
}
</style>

<script>
const columns = [
  {
    title: "用户昵称",
    dataIndex: "accountName",
    key: "accountName",
    scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "customRender",
    },
    onFilter: (value, record) =>
      record.accountName.toString().includes(value),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        }, 0);
      }
    },
  },
  {
    title: "用户账号",
    dataIndex: "account",
    key: "account",
    scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "customRender",
    },
    onFilter: (value, record) =>
      record.account.toString().toLowerCase().includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        });
      }
    },
  },
  {
    title: "被关注用户",
    dataIndex: "otherAccountName",
    key: "otherAccountName",
    scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "customRender",
    },
    onFilter: (value, record) =>
      record.otherAccountName.toString().includes(value),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        }, 0);
      }
    },
  },
  {
    title: "被关注用户的账号",
    dataIndex: "otherAccount",
    key: "otherAccount",
    scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "customRender",
    },
    onFilter: (value, record) =>
      record.otherAccount
        .toString()
        .toLowerCase()
        .includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        });
      }
    },
  },
  {
    title: "关注时间",
    dataIndex: "time",
    width: 180,
    sorter: (a, b) => new Date(a.time) - new Date(b.time),
  },
  {
    title: "操作",
    key: "action",
    scopedSlots: { customRender: "action" },
  },
];

const collectColumns = [
  {
    title: "用户昵称",
    dataIndex: "name",
    key: "name",
    scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "customRender",
    },
    onFilter: (value, record) =>
      record.name.toString().toLowerCase().includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        }, 0);
      }
    },
  },
  {
    title: "用户账号",
    dataIndex: "account",
    key: "account",
    scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "customRender",
    },
    onFilter: (value, record) =>
      record.account.toString().toLowerCase().includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        });
      }
    },
  },
  {
    title: "文章",
    dataIndex: "artic",
    key: "artic",
    scopedSlots: {
      filterDropdown: "filterDropdown",
      filterIcon: "filterIcon",
      customRender: "customRender",
    },
    onFilter: (value, record) =>
      record.artic.toString().toLowerCase().includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: (visible) => {
      if (visible) {
        setTimeout(() => {
          this.searchInput.focus();
        }, 0);
      }
    },
  },
  {
    title: "收藏时间",
    dataIndex: "time",
    width: 180,
    sorter: (a, b) => new Date(a.time) - new Date(b.time),
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
      collectData: [],
      collectColumns,

      searchText: "",
      searchInput: null,
      searchedColumn: "",

      selectedRowKeys: [],
    };
  },
  methods: {
    callback(key) {
      console.log(key);
    },
    handleSearch(selectedKeys, confirm, dataIndex) {
      confirm();
      this.searchText = selectedKeys[0];
      this.searchedColumn = dataIndex;
    },

    handleReset(clearFilters) {
      clearFilters();
      this.searchText = "";
    },

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
    delAttrntion(id,otherAccount){
      this.$axios
        .get(`api/func/removeAttention?id=${id}&otherAccount=${otherAccount}`)
        .then((res) => {
          this.getAttention();
          this.$message.success(`删除关注成功！`);
        })
        .catch((err) => {
          this.$message.error(`删除关注失败！`);
        });
    },
    delCollect(account,articID){
      console.log(account+','+articID)
      this.$axios
        .get(`api/func/removeCollect?account=${account}&articID=${articID}`)
        .then((res) => {
          this.getCollect();
          this.$message.success(`删除收藏成功！`);
        })
        .catch((err) => {
          this.$message.error(`删除收藏失败！`);
        });

    },
    getAttention() {
     this.$axios
        .get(`api/func/getAllAttention`)
        .then((res) => {
          this.data = res.data;
          for (let i = 0; i < this.data.length; i++) {
            this.data[i].key = i+1;
          }
          console.log(this.data)
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("获取关注内容失败");
        });
    },
    getCollect() {
      this.$axios
        .get(`api/func/getAllCollect`)
        .then((res) => {
          this.collectData = res.data;
          for (let i = 0; i < this.collectData.length; i++) {
            this.collectData[i].key = i+1;
          }
          console.log(this.collectData)
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("获取收藏内容失败");
        });
    },
  },
  mounted() {
    this.getAttention();
    this.getCollect();
  },
};
</script>