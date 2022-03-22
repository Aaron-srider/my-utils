package fit.wenchao.utils.reflect;

import java.util.jar.JarEntry;

public class JarEntryEnhancer {

    JarEntry jarEntry;

    private static final String CLASS_SUFFIX = ".class";
    private static final String META_INF_Dir = "META-INF";

    public JarEntryEnhancer(JarEntry jarEntry) {
        this.jarEntry = jarEntry;
    }

    /**
     * factory method
     * @param jarEntry 要enhance的jarEntry
     * @return 本类的一个实例
     */
    public static JarEntryEnhancer enhanceJarEntry(JarEntry jarEntry) {
        return new JarEntryEnhancer(jarEntry);
    }

    /**
     * 判断该entry是否是class文件
     */
    public boolean isClass() {
        return jarEntry.getName().endsWith(CLASS_SUFFIX);
    }

    /**
     * 判断该entry是否是包
     */
    public boolean isPackage() {
        String name = jarEntry.getName();
        if (jarEntry.isDirectory() && !name.startsWith(META_INF_Dir)) {
            return true;
        }
        return false;
    }

    /**
     * 获取全限定类名，包括包名和类名，如：com.example.AClass
     *
     * @return 如果本entry是类（以.class结尾），则返回全限定类名；否则抛出{@code IllegalStateException}异常。
     * @throws IllegalStateException 若本entry不是类，则抛出该异常。
     */
    public String getFullClassId() {
        if (isClass()) {
            return jarEntry.getName().substring(0, jarEntry.getName().length() - 6).replace("/", ".");
        } else {
            throw new IllegalStateException("entry '" + jarEntry.getName() + "' is not a class");
        }
    }

    /**
     * 判断该entry是否在指定的package下
     *
     * @param basePackage 指定的package
     * @return 是true否false
     */
    public boolean underSubPackage(String basePackage) {
        return jarEntry.getName().replace("/", ".").startsWith(basePackage);
    }

}