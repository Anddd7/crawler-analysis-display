package github.eddy.common;

/**
 * @author edliao 处理Html标签
 */
public class HtmlTools {

  /**
   * 除去html标签 ,只保留content
   */
  public static String removeHtmlTags(String string) {
    boolean ignore = false;
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < string.length(); i++) {
      Character c = string.charAt(i);
      if (c == '<') {
        ignore = true;
      } else if (string.charAt(i) == '>') {
        ignore = false;
        sb.append(" ");
      } else if (!ignore) {
        sb.append(c);
      }
    }
    return sb.toString()
        .replaceAll("&nbsp;", " ")
        .replaceAll("\n", " ")
        .replaceAll("\\s+", " ")
        .trim();
  }
}
