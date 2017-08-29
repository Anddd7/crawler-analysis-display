package github.eddy.crawler.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HtmlTools {

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

  public static String getStringAfter(String string, String prefix) {
    return string.substring(prefix.length()).trim();
  }
}
