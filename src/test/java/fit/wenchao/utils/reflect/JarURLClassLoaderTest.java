package fit.wenchao.utils.reflect;

import fit.wenchao.utils.string.StrUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.Runner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import static fit.wenchao.utils.basic.BasicUtils.forArr;
import static fit.wenchao.utils.basic.BasicUtils.gloop;
import static org.junit.jupiter.api.Assertions.*;

class JarURLClassLoaderTest {

    @Test
    void loadSubClass() throws Exception {

        String url1 = "jar:file:///Users/cw/Documents/Projects/Idea/MyUtils/target/my-utils-1.0.jar!/";
        String url2 = "jar:file:///Users/cw/Documents/库/junit-4.13-modified.jar!/";
        String url3 = "jar:file:///Users/cw/Documents/Projects/Idea/QuickSSL_test/resources/junit-4.13-wcmodified.jar!/";

        JarURLClassLoader jarURLClassLoader = new JarURLClassLoader(url3);

        Set<Class> classes = jarURLClassLoader.loadSubClass(Runner.class, "org.junit");
        Object[] objects = classes.stream().toArray();
        gloop(forArr(objects), (i, e, s) -> {
            StrUtils.outf("class name:{}", e.toString());
        });
    }
}