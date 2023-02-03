package fit.wenchao.utils.io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static fit.wenchao.utils.string.placeholderString.TemplateString.ft;


class ExistsFileTest {

    @Test
    public void copyTo() throws IOException {
        ExistsFile file = new ExistsFile("c:\\Users\\wc\\Desktop\\test");
        file.copyTo("c:\\Users\\wc\\Desktop\\test2");
    }

    @Test
    public void testCheckUsing() throws FileNotFoundException {
        ExistsFile file = new ExistsFile("c:\\hello");
        file.canRead();
    }

    @Test
    public void testLength() throws IOException {
        ExistsFile file = new ExistsFile("c:\\Users\\wc\\Desktop\\testMkdir");
        file.createNewFile();
    }


    @Test
    public void testCreateNewFile() throws IOException {
        ExistsFile file = new ExistsFile("c:\\Users\\wc\\Desktop\\微信录音 张伟 副院长_20220324103849.aac");
            file.createNewFile();
    }

    @Test
    public void testMkdir() throws IOException {
        ExistsFile file = new ExistsFile("c:\\Users\\wc\\Desktop\\testMkdir");
        file.mkdir();
    }

    @Test
    public void renameFileTo() throws IOException {
        ExistsFile file = new ExistsFile("c:\\Users\\wc\\Desktop\\test");
        file.moveTo(new ExistsFile(new File("c:\\\\Users\\\\wc\\\\Desktop\\testMkdir")), true);
        System.out.println(file);
        System.out.println(ft("file exists:{}", file.exists()));

        file.moveTo(new ExistsFile(new File(file.getParentFile().getParent())),true);
        System.out.println(file);
    }

    @Test
    public void renameDirTo() throws IOException {
        ExistsFile file = new ExistsFile("c:\\Users\\wc\\Desktop\\testMkdirxx");
        file.moveTo(new ExistsFile(new File("c:\\\\Users\\\\wc\\\\Desktop\\testMkdirxxx")), true);
        System.out.println(file);
        System.out.println(ft("file exists:{}", file.exists()));
    }
}