package github.eddy.bigdata.bilibili.common;

public enum TableEnum {
  source,
  analysis;

  public String getTableName(String module, String suffix) {
    return String.join("_", this.name(), module, suffix);
  }
}
