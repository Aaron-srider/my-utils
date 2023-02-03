package fit.wenchao.utils.reflect;

import org.junit.jupiter.api.Test;
import org.junit.runner.Runner;

import java.util.Set;

class JarURLClassLoaderTest {

    @Test
    void loadSubClass() throws Exception {

        String url1 = "jar:file:///Users/cw/Documents/Projects/Idea/MyUtils/target/my-utils-1.0.jar!/";
        String url2 = "jar:file:///Users/cw/Documents/库/junit-4.13-modified.jar!/";
        String url3 = "jar:file:///Users/cw/Documents/Projects/Idea/QuickSSL_test/resources/junit-4.13-wcmodified.jar!/";

        JarURLClassLoader jarURLClassLoader = new JarURLClassLoader(url3);

        Set<Class> classes = jarURLClassLoader.loadSubClass(Runner.class, "org.junit");
        Object[] objects = classes.stream().toArray();

    }
}