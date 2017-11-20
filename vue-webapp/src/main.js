/**
 * index.html是默认的主页
 * main.js是默认的主控脚本
 */
import Vue from 'vue'
//  引入入口文件
import App from './App.vue'
//  引入路由配置
import router from './config/router'
//  引入api
import cnodeAPI from './api/cnode_api'
Vue.prototype.$cnodeAPI = cnodeAPI
import utils from './utils/index'
Vue.prototype.$utils = utils
//引入element
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//引入fonticon
import '../static/iconfont/iconfont.css'


Vue.use(ElementUI)

Vue.config.productionTip = false

//  对index.html创建vue实例
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  /*
  在el下渲染一个template ,等效于下面的render函数
  template: '<App/>',
  components: {App}
  */
  render: (h) => h(App) // render渲染一个view并替换el元素 ; 箭头是ES6的lambda简写
})
