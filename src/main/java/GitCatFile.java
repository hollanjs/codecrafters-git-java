import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

enum GitObjFileType {
    BLOB,
    TREE,
    COMMIT
}

public class GitCatFile {
    private static int BUFFER_SIZE = 128;

    public static void parseGitFile(String hash) {
        try {
            Path filePath = Path.of(".git/objects", hash.substring(0, 2), hash.substring(2));
            byte[] fileBytes = Files.readAllBytes(filePath);

            // Modified documentation version of Inflator class to fit this use case
            ByteArrayOutputStream decompressedBaos = new ByteArrayOutputStream();
            try (Inflater decompressor = new Inflater()) {
                decompressor.setInput(fileBytes, 0, fileBytes.length);
                while (!decompressor.finished()) {
                    byte[] tmpBuffer = new byte[BUFFER_SIZE];
                    int numDecompressed = 0;
                    try {
                        numDecompressed = decompressor.inflate(tmpBuffer);
                    } catch (DataFormatException dfe) {
                        throw new RuntimeException(dfe);
                    }
                    decompressedBaos.write(tmpBuffer, 0, numDecompressed);
                }
            }

            // byte[] outputBytes = decompressedBaos.toByteArray();
            String outputString = decompressedBaos.toString(StandardCharsets.UTF_8);

            System.out.print(outputString.split("\0")[1]);
        } catch (Exception e) {
            System.err.print(e);
        }
    }
}
