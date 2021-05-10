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
          <a-input
            size="large"
            v-decorator="[
              'password',
              {
                rules: [
                  { required: true, message: 'Please confirm your Password!' },
                ],
              },
            ]"
            type="password"
            placeholder="Confirm Password"
          >
            <a-icon
              slot="prefix"
              type="lock"
              style="color: rgba(0, 0, 0, 0.25)"
            />
          </a-input>
        </a-form-item>
        <a-form-item>
          
          <a-button type="primary" html-type="submit" class="login-form-button">
            registered
          </a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </div>
</template>

<script>
export default {
  beforeCreate() {
    this.form = this.$form.createForm(this, { name: "normal_login" });
  },
  methods: {
    handleSubmit(e) {
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log("Received values of form: ", values);
        }
      });
    },
  },
};
</script>
<style lang='less'>
.ant-card-body {
  padding: 45px 24px;
}
.ant-col{
        margin-bottom: 30px;
    }

    .ant-form-item label{
        right: 48px
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
  background: url("../../../static/th.jpg") no-repeat;
  background-size: 100% 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  .ant-card {
    width: 380px;
    height: 350px;
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