package fit.wenchao.utils.reflect;

import sun.net.www.protocol.jar.JarURLConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;
import java.util.jar.JarFile;

public class JarURLClassLoader {
    private URL jar;
    private URLClassLoader classLoader;

    /**
     * jar的url格式为：jar:file:///xxx/xxx/xxx.jar!/
     * @param jar
     */
    public JarURLClassLoader(URL jar) {
        String protocol = jar.getProtocol();
        if(!"jar".equals(protocol)) {
            throw new IllegalArgumentException("the protocol of the url must be jar");
        }
        this.jar = jar;
        classLoader = new URLClassLoader(new URL[]{jar});
    }

    public JarURLClassLoader(String jarUrl) throws MalformedURLException {
        this(new URL(jarUrl));
    }

    /**
     * 从指定的包名下加载特定类及其的子类
     *
     * @param superClass  指定的父类
     * @param basePackage 从该包下寻找加载目标
     * @return 返回所有superClass及其子类
     */
    public Set<Class> loadSubClass(Class<?> superClass, String basePackage) throws IOException, ClassNotFoundException {
        JarFile jarFile = openJar();
        return doLoadSubClass(superClass, basePackage, jarFile);
    }

    private JarFile openJar() throws IOException {
        return ((JarURLConnection) jar.openConnection()).getJarFile();
    }

    private Set<Class> doLoadSubClass(Class<?> superClass, String basePackage, JarFile jarFile) throws ClassNotFoundException {
        JarClassesFinder jarClassFinder = new JarClassesFinder();
        return jarClassFinder.findClasses(classLoader, jarFile, basePackage, superClass);
    }


}
