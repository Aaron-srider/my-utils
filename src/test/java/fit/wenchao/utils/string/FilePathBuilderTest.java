package fit.wenchao.utils.string;

import org.junit.jupiter.api.Test;

class FilePathBuilderTest {

    @Test
    void l() {

        String end = FilePathBuilder.ofPath()
                                    .ct("")
                                    .build();
        System.out.println(end);

        String build = FilePathBuilder.ofPath()
                                      .ct("")
                                      .ct("")
                                      .ct("home")
                                      .build();
        System.out.println(build);
    }
}