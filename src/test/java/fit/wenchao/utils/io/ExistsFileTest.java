package fit.wenchao.utils.io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    public void renameTo() throws IOException {
        ExistsFile file = new ExistsFile("c:\\Users\\wc\\Desktop\\test");
        file.moveTo(new File("c:\\Users\\wc\\Desktop\\testMkdir"));
    }
}