package fit.wenchao.utils.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class IOUtilsTest {

    @Test
    void modifyASCIIFileThroughLine() throws IOException {
        ExistsFile existsFile = new ExistsFile("c:\\Users\\wc\\Desktop\\test");
        IOUtils.modifyASCIIFileThroughLine(existsFile, (line) ->line+="333");
        LineIterator lineIterator = FileUtils.lineIterator(existsFile.getUnderlyingFile());
        while(lineIterator.hasNext()){
            String s = lineIterator.nextLine();
            System.out.println(s);
        }
    }
}