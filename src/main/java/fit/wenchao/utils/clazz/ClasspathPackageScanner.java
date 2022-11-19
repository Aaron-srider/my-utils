package fit.wenchao.utils.clazz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This scanner is used to find out all classes in a package.
 */

public class ClasspathPackageScanner {

    private String basePackage;

    private ClassLoader cl;


    /**
     * Construct an instance with base package and class loader.
     *
     * @param basePackage The base package to scan.
     */
    public ClasspathPackageScanner(String basePackage) {
        this.basePackage = basePackage;
        this.cl = getClass().getClassLoader();
    }

    /**
     * Get all fully qualified names located in the specified package
     * and its sub-package.
     *
     * @return A list of fully qualified names.
     */
    public List<String> getFullyQualifiedClassNameList() throws IOException {
        return doScan(basePackage, new ArrayList<>());
    }

    public List<String> getFullyQualifiedClassNameList(String classFileSuffixName) throws IOException {
        return doScan(basePackage, new ArrayList<>(), classFileSuffixName);
    }

    /**
     * Get the path string of the base package from its url.
     *
     * @param basePackageResourceUrl The url represents base package resource location.
     * @return If the protocol of the url is "file:", return the url path string straightly.
     * If the protocol is "jar:file:", return the deepest jar file path. For example:
     * <pre>
     * package: com.example pro: file<br>
     * "file:/home/wc/com/example" -> "/home/wc/com/example"<br>
     * package: com.example pro: jar:file<br>
     * "jar:file:/home/fit/wc.jar!/lib/sec.jar!/com/example" -> "/home/fit/wc.jar!/lib/sec.jar"<br>
     * </pre>
     */
    private static String getPackPath(URL basePackageResourceUrl) {

        // return empty string if url is null
        if (basePackageResourceUrl == null) {
            return "";
        }

        // convert proto to "file:"
        String basePackResUrlString = basePackageResourceUrl.getFile();

        // find the flag "!" that represents the jar file
        int posOfExclamationPoint = basePackResUrlString.lastIndexOf('!');

        // ! not found, package not in a jar file, return
        if (-1 == posOfExclamationPoint) {
            return basePackResUrlString;
        }

        // get path after protocol string "file:"
        return basePackResUrlString.substring(5, posOfExclamationPoint);
    }

    /**
     * "cn.fh.lightning" -> "cn/fh/lightning"
     */
    private static String dotToSplash(String name) {
        return name.replaceAll("\\.", "/");
    }


    /**
     *
     * <p>
     * JarInputStream can only be directly created like below:
     * </p>
     * <pre>
     *     JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
     * </pre>
     * <p>
     * Code above wraps a FileInputStream with a JarInputStream, so we can read JarEntry from the
     * jar file conveniently.
     * </p>
     * <p>
     * But the problem is regular FileInputStream only accepts regular file path but not nested jar path,
     * like: /home/projects/java/demo2/target/demo2.jar!/BOOT-INF/lib/property-resolver-1.0-SNAPSHOT.jar,
     * so we need to get the raw InputStream in a special way.
     * </p>
     * <p>
     * We can use ClassLoader::getResourceAsStream to get the jar inputstream.
     * </p>
     *
     * This method use
     * @param jarPath
     * @return
     * @throws IOException
     */
    public JarInputStream getNestedJarInputStream(String jarPath) throws IOException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(jarPath);
        if (resourceAsStream != null) {
            return new JarInputStream(resourceAsStream);
        }
        return null;
    }


    /**
     * Adjudge whether a jar path is nested.
     * @param jarPath Target jar path;
     * @return True if the jar path is nested, false otherwise.
     */
    public static boolean isNestedJar(String jarPath) {
        String regex = "^(.*\\.jar!/)+.+\\.jar$";
        Pattern compile =
                Pattern.compile(regex);
        Matcher matcher = compile.matcher(jarPath);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count == 1;
    }

    /**
     * Get the deepest level jar path. Note that the return path is relative.
     */
    public static String getLastNestedJarRelativePath(String jarPath) {
        if (isNestedJar(jarPath)) {
            int i = jarPath.lastIndexOf("!/");
            if (i == -1) {
                throw new RuntimeException("Jar format error! " + jarPath);
            }
            return jarPath.substring(i + 2);
        }
        return jarPath;
    }

    /**
     * Read class files from a jar file which in the specific package.
     * @param basePackJarPathString Path string of a jar file. "/home/file/test.jar" for example.
     * @param splashedPackageName The splashed path form of a package. "com/example" for example.
     * @param classFileSuffixName The suffix of the class file, "class" default.
     * @return Return the names of any class file that end with ".${classFileSuffixName}" in the package
     * represents by "${splashedPackageName}"
     */
    private List<String> readFromJarFile(String basePackJarPathString,
                                         String splashedPackageName,
                                         String classFileSuffixName) throws IOException {
        JarInputStream jarIn;
        if (isNestedJar(basePackJarPathString)) {
            String lastNestedJarRelativePath = getLastNestedJarRelativePath(basePackJarPathString);
            jarIn = getNestedJarInputStream(lastNestedJarRelativePath);
        } else {
            jarIn = new JarInputStream(new FileInputStream(basePackJarPathString));
        }

        if (jarIn == null) {
            return new ArrayList<>();
        }

        JarEntry entry = jarIn.getNextJarEntry();

        List<String> nameList = new ArrayList<>();
        while (null != entry) {

            String name = entry.getName();
            if (name.contains(splashedPackageName) && isClassFile(name, classFileSuffixName)) {
                nameList.add(name);
            }

            entry = jarIn.getNextJarEntry();
        }

        return nameList;
    }

    /**
     * "Apple.class" -> "Apple"
     */
    private static String trimExtension(String name) {
        int pos = name.lastIndexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }

        return name;
    }

    private boolean isClassFile(String name) {
        return name.endsWith(".class" + "en");
    }

    private boolean isClassFile(String name, String classFileSuffixName) {
        return name.endsWith("." + classFileSuffixName);
    }

    /**
     * For test purpose.
     */
    public static void main(String[] args) throws Exception {
        ClasspathPackageScanner scan =
                new ClasspathPackageScanner("com.baomidou.mybatisplus.extension.activerecord");
        List<String> fullyQualifiedClassNameList = scan.getFullyQualifiedClassNameList();
        System.out.println(fullyQualifiedClassNameList);
    }

    /**
     * Actually perform the scanning procedure.
     *
     * @param basePackage Target package
     * @param nameList    A list to contain the result.
     * @return A list of fully qualified names.
     */
    private List<String> doScan(String basePackage, List<String> nameList) throws IOException {
        return this.doScan(basePackage, nameList, "class");
    }

    private List<String> doScan(String basePackage, List<String> nameList,String classFileSuffixName) throws IOException {
        // replace dots with splashes
        String basePackSplashedPath = dotToSplash(basePackage);


        URL basePackageResourceUrl = cl.getResource(basePackSplashedPath);

        // get package path
        String basePackPathString = getPackPath(basePackageResourceUrl);


        // Get classes in that package.
        // If the web server unzips the jar file, then the classes will exist in the form of
        // normal file in the directory.
        // If the web server does not unzip the jar file, then classes will exist in jar file.
        List<String> names = null; // contains the name of the class file. e.g: Apple.class will be stored as "Apple"

        // Judge if the basePackPathString is a jar file
        boolean isJar = isJarFile(basePackPathString);
        if (isJar) {
            names = readFromJarFile(basePackPathString, basePackSplashedPath, classFileSuffixName);
        } else {
            names = readFromDirectory(basePackPathString);
        }

        for (String name : names) {
            // If the package is in a jar file, readFromJarFile method will return all items in the jar file.
            if (isJar) {
                int i = name.indexOf(basePackSplashedPath);
                name = name.substring(i + basePackSplashedPath.length() + +1);
                name = name.replace("/", ".");
                //nameList.add(basePackage + "." + StringUtil.trimExtension(name));
                nameList.add(toFullyQualifiedName(name, basePackage));
            } else {
                // If the package is in a dir, readFromDirectory only read the first level of the file dir tree.
                // So recursion is required here if the cur name is represents a dir.
                if (isClassFile(name, classFileSuffixName)) {
                    int i = name.lastIndexOf("/");
                    if (i != -1) {
                        name = name.substring(i + 1);
                    }
                    //nameList.add(basePackage + "." + StringUtil.trimExtension(name));
                    nameList.add(toFullyQualifiedName(name, basePackage));
                } else {
                    // this is a directory
                    // check this directory for more classes
                    // do recursive invocation
                    doScan(basePackage + "." + name, nameList);
                }
            }
        }

        return nameList;
    }

    /**
     * Convert short class file name to fully qualified name.
     * e.g., String -> java.lang.String
     */
    private String toFullyQualifiedName(String classFileName, String basePackage) {
        return basePackage + '.' +
                trimExtension(classFileName);
    }

    /**
     * Read the first level of the base package dir.
     * @param basePackPathString Path string of the base package.
     * @return All files including dirs in the first level of the base package dir.
     */
    private List<String> readFromDirectory(String basePackPathString) {
        File file = new File(basePackPathString);
        String[] names = file.list();

        if (null == names) {
            return new ArrayList<>();
        }

        return Arrays.asList(names);
    }

    private boolean isJarFile(String name) {
        if (name == null) {
            return false;
        }
        return name.endsWith(".jar");
    }

}