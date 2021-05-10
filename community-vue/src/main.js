// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';

Vue.use(Antd);

// import VueI18n from 'vue-i18n'
// 
// Vue.use(VueI18n);

Vue.config.productionTip = false
Vue.prototype.$axios = axios


/* eslint-disable no-new */
new Vue({
  el: '#app',
  // i18n,
  router,
  components: { App },
  template: '<App/>'
})
