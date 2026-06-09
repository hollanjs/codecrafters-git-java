import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
  public static void main(String[] args) {

    final String command = args[0];

    switch (command) {
      case "cat-file" -> {
        if ("-p".equals(args[1]) && args[2] != null) {
          GitCatFile.parseGitFile(args[2]);
        }
      }

      case "hash-object" -> {
        if (args[1] != null) {
          String fileHash = GitHashObject.generateGitFileHash(args[1]);
          System.out.println(fileHash);
        }
      }

      case "init" -> {
        final File root = new File(".git");
        new File(root, "objects").mkdirs();
        new File(root, "refs").mkdirs();
        final File head = new File(root, "HEAD");

        try {
          head.createNewFile();
          Files.write(head.toPath(), "ref: refs/heads/main\n".getBytes());
          System.out.println("Initialized git directory");
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      default -> System.out.println("Unknown command: " + command);
    }
  }
}
