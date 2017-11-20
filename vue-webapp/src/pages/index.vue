<template>
  <div>
    <el-table class="articleList" v-loading="loading" :data="tableData" stripe style="width: 100%" @row-click="rowClick">
      <el-table-column label="标题" minwidth="180">
        <template slot-scope="scope">
          <i class="icon el-icon-caret-top" v-if="scope.row.top"></i>
          <i class="icon el-icon-star-on" v-if="scope.row.good"></i>
          <span>{{ scope.row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="create_at" label="日期" width="160" :formatter="timeFormatter">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        tableData: [],
        loading: false
      }
    },
    created() {
      this.getData()
    },
    methods: {
      getData: function (params) {
        this.loading = true
        this.$cnodeAPI.get(
          "topics",
          params ? params : {},
          data => {
            this.tableData = data.data
            this.loading = false
          },
          error => console.log(error.statusText)
        )
      },
      timeFormatter: function (row, col, value) {
        return this.$utils.goodTime(value)
      },
      rowClick: function (row, event, column) {
        this.$router.push({
          path: '/content/' + row.id
        })
      }
    }
  }

</script>
