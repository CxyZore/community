<template>
  <a-layout id="components-layout-demo-side" style="min-height: 100vh">
    <a-layout-sider v-model="collapsed" collapsible>
      <div class="logo" @click="toJump('welcome')" />

      <a-menu theme="dark" mode="inline">
        <!-- 标签页：文章、内容 -->
        <a-sub-menu key="1">
          <span slot="title"
            ><a-icon type="container" /><span>内容管理</span></span
          >
          <a-menu-item key="2" @click="toJump('content/content')">
            内容管理
          </a-menu-item>
          <a-menu-item key="3" @click="toJump('content/label')">
            标签管理
          </a-menu-item>
          <a-menu-item key="4" @click="toJump('content/recommend')">
            推荐管理
          </a-menu-item>
          <a-menu-item key="5" @click="toJump('content/comments')">
            评论管理
          </a-menu-item>
          <a-menu-item key="6" @click="toJump('content/focus')"
            >关注收藏
          </a-menu-item>
        </a-sub-menu>

        <a-menu-item key="10" @click="toJump('message')">
          <a-icon type="highlight" />
          <span>留言管理</span>
        </a-menu-item>

        <a-sub-menu key="permission">
          <span slot="title"><a-icon type="crown" /><span>权限管理</span></span>
          <a-menu-item key="11" @click="toJump('permission/administrator')">
            管理员管理
          </a-menu-item>
          <a-menu-item key="12" @click="toJump('permission/role')">
            角色管理
          </a-menu-item>
        </a-sub-menu>

        <a-menu-item key="user" @click="toJump('user')">
          <a-icon type="user" />
          <span>用户管理</span>
        </a-menu-item>

        <a-menu-item key="environment" @click="toJump('map')">
          <a-icon type="environment" />
          <span>地图</span>
        </a-menu-item>
        <!-- <a-menu-item>
          <a-icon type="code" />
          <span>关于我们</span>
        </a-menu-item> -->
      </a-menu>
    </a-layout-sider>

    <a-layout>
      <a-layout-header>
        <a-row type="flex">
          <!-- 可能会加点什么 -->
          <a-col :span="4">
            <a-breadcrumb>
              <a-breadcrumb-item v-if="this.breadcrumbList.length === 0"
                >WELCOME!</a-breadcrumb-item
              >
              <a-breadcrumb-item v-for="key in breadcrumbList" :key="key">{{
                key
              }}</a-breadcrumb-item>
              <!-- <a-breadcrumb-item>Bill</a-breadcrumb-item> -->
            </a-breadcrumb>
          </a-col>
          <a-col :span="15" />

          <a-col :span="5">
            <a-space :size="35">
              <a-badge dot>
                <a-icon type="message" />
              </a-badge>
              <a-icon type="github" />

              <a-popover placement="bottom">
                <template slot="content">
                  <ul class="personal_setting">
                    <li><a @click="toJump('personal')">个人设置</a></li>
                    <li><a @click="toChangePassword">修改密码</a></li>
                    <li><a @click="toJump('Login')">退出账户</a></li>
                  </ul>
                </template>
                <a-avatar style="background-color: #87d068" icon="user" />
              </a-popover>

              <!-- 主题/国际化设置等 -->
              <a-icon type="setting" theme="filled" />
            </a-space>
          </a-col>
        </a-row>
      </a-layout-header>

      <a-layout-content style="margin-top: 20px">
        <router-view></router-view>
      </a-layout-content>

      <a-layout-footer style="text-align: center">
        Community ©2020 Created by CXY
      </a-layout-footer>
    </a-layout>

    <a-modal
      title="修改密码"
      :visible="visible"
      :confirm-loading="confirmLoading"
      @ok="handleOk"
      @cancel="handleCancel"
    >
      <a-form-model
        ref="ruleForm"
        :model="form"
        :rules="rules"
        :label-col="{ span: 7 }"
        :wrapper-col="{ span: 14 }"
      >
        <a-form-model-item
          has-feedback
          ref="newPassword"
          label="新密码"
          prop="newPassword"
        >
          <a-input v-model="form.newPassword" />
        </a-form-model-item>
        <a-form-model-item
          has-feedback
          ref="confirmPassword"
          label="确认密码"
          prop="confirmPassword"
        >
          <a-input v-model="form.confirmPassword" />
        </a-form-model-item>
        <a-form-model-item
          has-feedback
          ref="oldPassword"
          label="旧密码"
          prop="oldPassword"
        >
          <a-input v-model="form.oldPassword" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </a-layout>
</template>
<script>
export default {
  data() {
    let validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("Please input the password"));
      } else {
        if (this.form.confirmPassword !== "") {
          this.$refs.ruleForm.validateField("confirmPassword");
        }
        callback();
      }
    };
    let confiemPass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("Please input the password again"));
      } else if (value !== this.form.newPassword) {
        callback(new Error("Two inputs don't match!"));
      } else {
        callback();
      }
    };
    return {
      collapsed: false,
      breadcrumbList: [],
      visible: false,
      confirmLoading: false,
      form: {
        newPassword: "",
        confirmPassword: "",
        oldPassword: "",
      },
      rules: {
        newPassword: [
          { required: true, validator: validatePass, trigger: "change" },
        ],
        confirmPassword: [
          { required: true, validator: confiemPass, trigger: "change" },
        ],
        oldPassword: [
          {
            required: true,
            message: "Please input old password",
            trigger: "blur",
          },
        ],
      },
      user: {},
    };
  },
  methods: {
    toJump(val) {
      if (val === "Login") {
        this.$router.push("/Login");
      } else {
        this.$router.push(`/Home/${val}`);
      }
    },
    getBreadcrumb() {
      const map = new Map([
        ["welcome", "WELCOME!"],
        ["personal", "个人信息"],
        ["content", "内容管理"],
        ["label", "标签管理"],
        ["recommend", "推荐管理"],
        ["comments", "评论管理"],
        ["focus", "关注收藏"],
        ["message", "留言管理"],
        ["permission", "权限管理"],
        ["administrator", "管理员管理"],
        ["role", "角色管理"],
        ["user", "用户管理"],
        ["map", "定位地图"],
      ]);

      this.breadcrumbList = this.$route.matched[1].path.split("/");

      this.breadcrumbList.splice(0, 2);
      for (let i = 0; i < this.breadcrumbList.length; i++) {
        this.breadcrumbList[i] = map.get(this.breadcrumbList[i]);
      }
    },

    toChangePassword() {
      this.visible = true;
    },
    handleOk(e) {
      this.ModalText = "The modal will be closed after two seconds";
      this.confirmLoading = true;
      setTimeout(() => {
        this.visible = false;
        this.confirmLoading = false;
      }, 200);
    },
    handleCancel(e) {
      console.log("Clicked cancel button");
      this.visible = false;
    },
  },
  watch: {
    $route() {
      this.getBreadcrumb();
    },
  },
};
</script>

<style lang='less'>
* {
  margin: 0;
  padding: 0;
}

#components-layout-demo-side .logo {
  height: 45px;
  margin: 15px 23px;
  background: url("../../../static/log.jpeg");
  background-size: cover;
}

.ant-menu-dark.ant-menu-inline .ant-menu-item,
.ant-menu-dark.ant-menu-inline .ant-menu-submenu-title {
  text-align: left;
}

/* latin-ext */
@font-face {
  font-family: "Lato";
  font-style: normal;
  font-weight: 400;
  src: local("Lato Regular"), local("Lato-Regular"),
    url(https://fonts.gstatic.com/s/lato/v17/S6uyw4BMUTPHjxAwXjeu.woff2)
      format("woff2");
  unicode-range: U+0100-024F, U+0259, U+1E00-1EFF, U+2020, U+20A0-20AB,
    U+20AD-20CF, U+2113, U+2C60-2C7F, U+A720-A7FF;
}
/* latin */
@font-face {
  font-family: "Lato";
  font-style: normal;
  font-weight: 400;
  src: local("Lato Regular"), local("Lato-Regular"),
    url(https://fonts.gstatic.com/s/lato/v17/S6uyw4BMUTPHjx4wXg.woff2)
      format("woff2");
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02BB-02BC, U+02C6, U+02DA,
    U+02DC, U+2000-206F, U+2074, U+20AC, U+2122, U+2191, U+2193, U+2212, U+2215,
    U+FEFF, U+FFFD;
}

.personal_setting {
  display: flex;
  flex-direction: column;
  align-items: start;
  list-style-type: none;

  li {
    padding: 6px 0;

    a {
      position: relative;
      display: block;
      padding: 4px 0;
      font-family: Lato, sans-serif;
      color: black;
      text-decoration: none;
      text-transform: uppercase;
      transition: 0.5s;

      &::after {
        position: absolute;
        content: "";
        top: 100%;
        left: 0;
        width: 100%;
        height: 2px;
        background: #3498db;
        transform: scaleX(0);
        transform-origin: right;
        transition: transform 0.5s;
      }

      &:hover {
        color: #95a5a6;
      }

      &:hover::after {
        transform: scaleX(1);
        transform-origin: left;
      }
    }
  }
}
</style>

<style lang='less' scoped>
.ant-layout-header {
  background-color: #f0f2f5;
  padding: 0;
  font-size: 26px;
  .ant-breadcrumb {
    text-align: left;
    padding: 25px 0 0 20px;
    font-size: initial;
  }
  .anticon-message {
    width: 22px;
    height: 22px;
    line-height: 22px;
    font-size: 22px;
  }
  .ant-avatar.ant-avatar-icon {
    top: -2px;
  }
}
</style>