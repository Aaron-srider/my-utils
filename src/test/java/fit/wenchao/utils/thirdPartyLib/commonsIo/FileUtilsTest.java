package fit.wenchao.utils.thirdPartyLib.commonsIo;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileUtilsTest {

    @Test
    public void readLines() throws IOException {
        File myfile = new File("src/main/resources/words.txt");

        String contents = FileUtils.readFileToString(myfile,
                StandardCharsets.UTF_8.name());

        System.out.println(contents);

        List<String> lines = FileUtils.readLines(myfile,
                StandardCharsets.UTF_8.name());

        System.out.printf("There are %d lines in the file\n", lines.size());

        System.err.printf("The second line is: %s", lines.get(1));
    }

    @Test
    public void createDelFile() throws IOException {
        File myfile = new File("src/main/resources/myfile.txt");
        org.apache.commons.io.FileUtils.touch(myfile);

        if (myfile.exists()) {

            System.out.println("The file exists");
        } else {

            System.out.println("The file does not exist");
        }

        org.apache.commons.io.FileUtils.deleteQuietly(myfile);

        if (myfile.exists()) {

            System.out.println("The file exists");
        } else {

            System.out.println("The file does not exist");
        }
    }

    @Test
    public void cp() throws IOException {

        File myfile1 = new File("src/main/resources/myfile.txt");
        File myfile2 = new File("src/main/resources/myfile2.txt");

        FileUtils.copyFile(myfile1, myfile2);

        if (FileUtils.contentEquals(myfile1, myfile2)) {

            System.out.println("The files have equal content");
        } else {

            System.out.println("The files do not have equal content");
        }

        File docs = new File("src/main/resources/docs");
        FileUtils.forceMkdir(docs);

        FileUtils.copyFileToDirectory(myfile2, docs);
    }

}