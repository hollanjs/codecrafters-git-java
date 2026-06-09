import java.io.File;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public class GitHashObject {
    public static String generateGitFileHash(String fileName) {
        try {
            Path dir = Paths.get("").toAbsolutePath();
            Path foundFile = Path.of(dir.toString(), fileName);

            System.out.println(foundFile.toString());
            MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");

            byte[] fileBytes = Files.readAllBytes(foundFile);
            byte[] fileHash = sha1Digest.digest(fileBytes);

            StringBuilder sb = new StringBuilder();
            for (byte b : fileHash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // catch (FileNotFoundException e) {
        // System.err.print(e);
        // } catch (NoSuchAlgorithmException e) {
        // System.err.print(e);
        // }
    }

    public static void writeHashedGitFile(String filehashString, String filePath) {

    }
}
