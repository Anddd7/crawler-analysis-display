package github.eddy.common;

import static github.eddy.common.FileSystemTool.getAbsoluteClassPath;
import static github.eddy.common.FileSystemTool.getProjectPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProtobufTool {

  public static void compile(String srcPath, String fileName)
      throws IOException, InterruptedException {
    compile(DEFAULT_PROTOC, srcPath, fileName);
  }

  public static void compile(String protocPath, String srcPath, String fileName)
      throws IOException, InterruptedException {
    String command = new StringBuilder()
        .append(protocPath)//protoc.exe
        .append(" -I=").append(getAbsoluteClassPath())// -I=resources
        .append(" --java_out=").append(srcPath) // --java_out=/src/main/java
        .append(" ").append(getAbsoluteClassPath() + "/" + fileName) // resources/xxx.proto
        .toString();

    Process p = Runtime.getRuntime().exec(command);

    new BufferedReader(new InputStreamReader(p.getInputStream())).lines()
        .forEach(s -> System.out.println(s));

    new BufferedReader(new InputStreamReader(p.getErrorStream())).lines()
        .forEach(s -> System.out.println(s));

    p.waitFor();
    p.destroy();
  }

  public static String DEFAULT_PROTOC = getProjectPath() + "/bin/protoc.exe";
}
