package fit.wenchao.utils.reflect;

import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarClassesFinder {
    public Set<Class> findClasses(URLClassLoader classLoader, JarFile jarFile, String basePackage, Class<?> superClass) {
        Set<Class> classes = new HashSet<>();
        Enumeration<JarEntry> entries = jarFile.entries();
        Class<?> clazz = null;
        while (entries.hasMoreElements()) {
            JarEntryEnhancer jarEntry = new JarEntryEnhancer(entries.nextElement());

            if (!jarEntry.isClass() || !jarEntry.underSubPackage(basePackage)) {
                continue;
            }

            String fullClassId = jarEntry.getFullClassId();

            //不加载内部类
            if(fullClassId.contains("$")) {
                continue;
            }

            try {
                clazz = classLoader.loadClass(fullClassId);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (clazz != null && !clazz.isInterface() && superClass.isAssignableFrom(clazz)) {
                classes.add(clazz);
            }

            if (clazz != null && !clazz.isInterface() && superClass.isAssignableFrom(clazz)) {
                classes.add(clazz);
            }
        }
        return classes;
    }
}
