import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Login from '@/view/login/login'
import Registered from '@/view/login/registered'
import Home from '@/view/home/home'

Vue.use(Router)

export default new Router({
  routes: [
    { path: '/', redirect: '/Login' },
    {
    path: '/Home',
    redirect: '/Home/welcome',
    component: Home,
    children: [{
      path: 'welcome',
      component: () => import('@/view/page/welcome')
    },{
      path: 'personal',
      component: () => import('@/view/page/personal')
    },{
      path: 'content/content',
      component: () => import('@/view/page/content/content')
    },{
      path: 'content/label',
      component: () => import('@/view/page/content/label')
    },{
      path: 'content/recommend',
      component: () => import('@/view/page/content/recommend')
    },{
      path: 'content/comments',
      component: () => import('@/view/page/content/comments')
    },{
      path: 'content/focus',
      component: () => import('@/view/page/content/focus')
    },{
      path: 'message',
      component: () => import('@/view/page/message')
    },{
      path: 'permission/administrator',
      component: () => import('@/view/page/permission/administrator')
    },{
      path: 'permission/role',
      component: () => import('@/view/page/permission/role')
    },{
      path: 'user',
      component: () => import('@/view/page/user')
    },{
      path: 'map',
      component: () => import('@/view/page/map')
    },
  ]
  }, {
    path: '/Login',
    name: 'Login',
    component: Login
  }, {
    path: '/Registered',
    name: 'Registered',
    component: Registered
  }, {
    path: '/HelloWorld',
    name: 'HelloWorld',
    component: HelloWorld
  }]
})
