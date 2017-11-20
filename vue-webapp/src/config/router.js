/**
 * 路由配置
 */
//  引用vue包
import Vue from 'vue'
import VueRouter from 'vue-router'
//  引用路由页面
import index from '@/pages/index.vue'
import content from '@/pages/content.vue'
//  引用子路由组件
import Frame from '@/components/subrouter.vue'
//  引用子路由页面
import userIndex from '@/pages/user/index.vue'
import userInfo from '@/pages/user/info.vue'
import userLove from '@/pages/user/love.vue'

//  使用路由
Vue.use(VueRouter)
//  配置路由数组 ,通过export导出给其他文件使用
export default new VueRouter({
  routes: [{
    path: '/',
    component: index
  }, {
    path: '/content/:id',
    component: content
  }, {
    path: '/user',
    component: Frame,
    children: [{
      path: '/',
      component: userIndex
    }, {
      path: 'info',
      component: userInfo
    }, {
      path: 'love',
      component: userLove
    }]
  }]
})
