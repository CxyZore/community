<template>
  <div id="body">
    <a-card>
      <a-form
        id="components-form-demo-normal-login"
        :form="form"
        class="login-form"
        @submit="handleSubmit"
      >
        <a-form-item>
          <a-input
            size="large"
            v-decorator="[
              'userName',
              {
                rules: [
                  { required: true, message: 'Please input your username!' },
                ],
              },
            ]"
            placeholder="Username"
          >
            <a-icon
              slot="prefix"
              type="user"
              style="color: rgba(0, 0, 0, 0.25)"
            />
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-input
            size="large"
            v-decorator="[
              'password',
              {
                rules: [
                  { required: true, message: 'Please input your Password!' },
                ],
              },
            ]"
            type="password"
            placeholder="Password"
          >
            <a-icon
              slot="prefix"
              type="lock"
              style="color: rgba(0, 0, 0, 0.25)"
            />
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-checkbox
            v-decorator="[
              'remember',
              {
                valuePropName: 'checked',
                initialValue: true,
              },
            ]"
          >
            Remember me
          </a-checkbox>
          <a class="login-form-forgot" href=""> Forgot password </a>
          <a-button type="primary" html-type="submit" class="login-form-button">
            Log in
          </a-button>
          Or
          <a href=""> register now! </a>
        </a-form-item>
      </a-form>
    </a-card>
  </div>
</template>

<script>
import { setCookie, getCookie, delCookie } from "../../util/util";
export default {
  data() {
    return {};
  },
  beforeCreate() {
    this.form = this.$form.createForm(this, { name: "normal_login" });
  },
  methods: {
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
          this.$axios
            .get(`api/user/login?account=${values.userName}&pd=${values.password}`)
            .then((res) => {
              // console.log(res);
              if (res.data) {
                this.$message.success("登录成功");
                setCookie("account", values.userName, 1);

                this.$router.push("/home/welcome");
              } else {
                this.$message.error("密码错误");
              }
            })
            .catch((err) => {
              this.$message.error("登录失败");
            });
        }
      });
    },
    del(){
      delCookie('account');
    }
  },
  mounted() {
    this.del();
  }
};
</script>
<style lang='less'>
.ant-card-body {
  padding: 45px 24px;
}
.ant-col {
  margin-bottom: 30px;
}

.ant-form-item label {
  right: 48px;
}
</style>
<style lang='less' scoped>
* {
  margin: 0;
  padding: 0;
}
#body {
  height: 100%;
  width: 100%;
  background: url("../../../static/4.jpg") no-repeat;
  background-size: 100% 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  .ant-card {
    width: 380px;
    height: 340px;
    border-radius: 8px;
  }
}

#components-form-demo-normal-login {
  .login-form {
    max-width: 300px;
  }
  .login-form-forgot {
    float: right;
  }
  .login-form-button {
    width: 100%;
  }
}
</style>