package github.eddy.bigdata.core.common;

import static java.lang.String.join;

public enum TableEnum {
  source,
  analysis;

  public String table(String module, String suffix) {
    return join("_", this.name(), module, suffix);
  }
}
