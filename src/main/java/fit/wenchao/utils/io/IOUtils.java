package fit.wenchao.utils.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;
import java.util.UUID;
import java.util.function.Function;

import static fit.wenchao.utils.string.StrUtils.ft;
import static java.util.Arrays.asList;

@Slf4j
public class IOUtils {
    private static File createTempFile(String filename) throws IOException {
        File tempConfigVmxFile = new File(filename);
        try {
            tempConfigVmxFile.createNewFile();
            return tempConfigVmxFile;
        } catch (IOException e) {
            log.error("An I/O error occurred when create temp file:" + filename);
            throw e;
        }
    }

    private static BufferedWriter getFileWriter(File targetFile) throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile)));
    }

    static BufferedReader getFileReader(File targetFile) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(targetFile)));
    }

    private static void mapContentEachLine(ExistsFile sourceFile, ExistsFile targetFile,
                                           Function<String, String> lineChange) throws IOException {

        try (BufferedReader configVmxFileBufferedReader = getFileReader(sourceFile.getUnderlyingFile());
             BufferedWriter newConfigVmxFileBufferedWrite = getFileWriter(targetFile.getUnderlyingFile());) {
            String line = null;
            String mappedLine = null;
            int lineNum = 0;
            while ((line = configVmxFileBufferedReader.readLine()) != null) {
                lineNum++;
                mappedLine = lineChange.apply(line);
                newConfigVmxFileBufferedWrite.write(mappedLine);
                newConfigVmxFileBufferedWrite.write("\n");
                if (!mappedLine.equals(line)) {
                    log.info("map line:{} from:{} to:{}", lineNum, line, mappedLine);
                }
            }
            newConfigVmxFileBufferedWrite.flush();
        }
    }

    /**
     * 按行为单位修改一个ASCII文件的内容，用户可以指定如何对单独的一行进行处理。
     *
     * @param originFile 原始文件
     * @param lineMap    对单独一行进行的映射
     * @return 返回代表修改后文件的 ExistsFile 对象
     * @throws IOException
     */
    public static ExistsFile modifyASCIIFileThroughLine(ExistsFile originFile, Function<String, String> lineMap) throws IOException {
        String tempFilePath = ft("{}{}temp-{}", originFile.getParent(), File.separator, UUID.randomUUID().toString());
        ExistsFile existsTempFile = new ExistsFile(tempFilePath);

        existsTempFile.createNewFile();
        log.info("create temp file \"{}\"", existsTempFile);

        mapContentEachLine(originFile, existsTempFile, lineMap);
        originFile.delete();
        log.info("delete original file \"{}\"", originFile);

        existsTempFile.moveTo(originFile, false);
        log.info("rename temp file \"{}\" to \"{}\"", tempFilePath, originFile);

        return existsTempFile;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        File file = new File("/Users/martin6699/test.txt");
        printPaths(file);

        // relative path
        file = new File("test.xsd");
        printPaths(file);
        // complex relative paths
        file = new File("/Users/martin6699/../martin6699/test.txt");
        printPaths(file);
        // URI paths
        file = new File(new URI("file:///Users/martin6699/test.txt"));
        printPaths(file);

        // symbolic links  软链接 /Users/martin6699/logs ---> /tmp/Data/logs
        file = new File("/Users/martin6699/logs/test.txt");
        printPaths(file);

    }

    private static void printPaths(File file) throws IOException {
        System.out.println("Absolute Path: " + file.getAbsolutePath());
        System.out.println("Canonical Path: " + file.getCanonicalPath());
        System.out.println("Path: " + file.getPath());
        System.out.println("----------------------------------------------------------------");
    }
}
