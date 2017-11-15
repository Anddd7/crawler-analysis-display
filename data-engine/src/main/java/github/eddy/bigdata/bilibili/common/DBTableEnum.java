package github.eddy.bigdata.bilibili.common;

import static java.lang.String.join;

/**
 * 业务相关MongoDB表名管理
 *
 * @author edliao
 */
public enum DBTableEnum {
    //抓取的元数据
    source_search,
    //分析结果
    analysis_tag_count, analysis_category_data,
    //系统配置
    sys_task_record, sys_category_type;

    public String table(String... suffix) {
        return join("_", this.name(), join("_", suffix));
    }
}
