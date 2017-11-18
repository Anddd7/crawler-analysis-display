package github.eddy.common;

/**
 * @author edliao on 2017/6/23. 文件系统工具
 */
public class FileSystemTool {

  /**
   * 获取当前classpath
   *
   * @apiNote 在IDEA中和 'use ClassPath of module' 设置有关
   */
  public static String getAbsoluteClassPath() {
    String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    return path.substring(1, path.length());
  }

  /**
   * 获取项目路径
   *
   * @apiNote 在IDEA中和 'working directory' 设置有关
   */
  public static String getProjectPath() {
    return System.getProperty("user.dir");
  }
}
