<template>
  <div id="app">
    <myHeader></myHeader>
    <router-view class="mainView" keep-alive transition transition-mode="out-in">
    </router-view>
    <myFooter></myFooter>
  </div>
</template>

<script>
  //引用组件
  import myHeader from "./components/header.vue";
  import myFooter from "./components/footer.vue";
  //为这个页面的组件设置data节点
  export default {
    //声明组件
    components: {
      myHeader,
      myFooter
    }
  };

</script>

<!-- css用scss写在文件中 -->
<style lang="scss">
  @import "./style/style";

</style>
