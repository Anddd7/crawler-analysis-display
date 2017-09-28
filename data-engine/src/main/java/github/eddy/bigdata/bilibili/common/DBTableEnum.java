package github.eddy.bigdata.bilibili.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.join;

public enum DBTableEnum {
    source_search,
    analysis_tagcount, analysis_categorydata;

    public static String TASK_RECORD = "task_record";

    public String table(String... suffix) {
        List<String> list = new ArrayList();
        list.add(this.name());
        list.addAll(Arrays.asList(suffix));
        return join("_", list);
    }
}
