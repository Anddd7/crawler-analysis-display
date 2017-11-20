<template>
  <div>
    <div class="content">
      <el-button class="floatBtn" icon="el-icon-arrow-left" @click="backList">首页</el-button>
      <h2 v-text="dat.title"></h2>
      <el-row type="flex">
        <el-col :span="6">
          <p v-if="dat.author">作者：{{dat.author.loginname}}</p>
        </el-col>
        <el-col :span="6" style="text-align:end;">
          <p>发布时间:{{$utils.formatDate(dat.create_at,'yyyy-MM-dd hh:mm:ss')}}</p>
        </el-col>
      </el-row>
      <div class="tags">
        <el-tag type="danger" v-if="dat.top">置顶</el-tag>
        <el-tag v-if="dat.good">精品</el-tag>
        <el-tag type="info">{{dat.tab_des}}</el-tag>
        <el-tag type="success">阅读:{{dat.visit_count}}</el-tag>
        <el-tag type="warning">回复:{{dat.replies_count}}</el-tag>
      </div>
      <hr>

      <article v-html="dat.content"></article>

      <div id="anchor-replies">
        <hr>
        <h2>网友回复：</h2>
        <ul class="replies">
          <li v-for="i in dat.replies">
            <el-row type="flex" justify="space-between">
              <el-col :span="6">评论者：{{i.author.loginname}}</el-col>
              <el-col :span="12"></el-col>
              <el-col :span="6" style="text-align:end;">{{$utils.goodTime(i.create_at)}}</el-col>
            </el-row>
            <article v-html="i.content"></article>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        id: this.$route.params.id,
        dat: {}
      }
    },
    created() {
      this.getData()
    },
    methods: {
      getData() {
        this.$cnodeAPI.get('topic/' + this.id, null, r => {
          var data = r.data
          data.replies_count = data.replies.length;
          switch (data.tab) {
            case 'ask':
              data.tab_des = '问答'
              break
            case 'share':
              data.tab_des = '分享'
              break
            case 'job':
              data.tab_des = '招聘'
              break
            case 'dev':
              data.tab_des = '开发'
              break
            default:
          }
          this.dat = data;
        })
      },
      backList: function () {
        this.$router.push({
          path: '/'
        })
      }
    }
  }

</script>
