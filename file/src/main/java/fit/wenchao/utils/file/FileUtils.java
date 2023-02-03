package fit.wenchao.utils.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class FileUtils {
    public static String relativize(String parentPath, String subPath) {
        return Paths.get(parentPath)
                    .toAbsolutePath()
                    .relativize(Paths.get(subPath).toAbsolutePath())
                    .toString();
    }

    public static String relativize(File parentFile, File subFile) {
        return parentFile.getAbsoluteFile()
                         .toPath()
                         .relativize(subFile.getAbsoluteFile().toPath())
                         .toString();
    }

    public static boolean isSymbolicLink(File f) throws IOException {
        return !f.getAbsolutePath()
                 .equals(f.getCanonicalPath());
    }

    public static boolean deleteAll(String absolutePath) {
        if (Files.exists(Paths.get(absolutePath)) && Files.isDirectory(Paths.get(absolutePath))) {
            //List<String> allFilePath = new ArrayList<>();
            try (Stream<Path> walk = Files.walk(Paths.get(absolutePath))) {
                walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .peek((file) ->
                    {
                        String fileAbsolutePath = file.getAbsolutePath();
                        //allFilePath.add(fileAbsolutePath);
                    })
                    .forEach(File::delete);
                return true;
            }
            catch (IOException e) {
                return false;
            }
        }
        return false;
    }
}
