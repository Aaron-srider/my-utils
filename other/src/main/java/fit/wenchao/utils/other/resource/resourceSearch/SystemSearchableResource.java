package fit.wenchao.utils.other.resource.resourceSearch;


import sun.misc.URLClassPath;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Search resources through classloader, which means that a resource can always
 * be found if it can be found correctly in the classpath.
 */
public class SystemSearchableResource implements ISearchableResource {

    private static final SystemSearchableResource SINGLETON =
            new SystemSearchableResource();

    public static SystemSearchableResource getSingleton() {
        return SINGLETON;
    }

    public static <T> List<T> removeDuplicationByTreeSet(List<T> list) {
        TreeSet<T> set = new TreeSet<>(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    private static String getDirJarPath(URL basePackageResourceUrl) {

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
     * Adjudge whether a jar path is nested.
     *
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

    private <T> T getFieldFromObject(
            Object obj,
            String fieldName,
            Class<T> tClass
    ) throws IllegalAccessException, NoSuchFieldException {
        List<Field> fieldList = new ArrayList<>();
        Class<?> tempClass = obj.getClass();
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }
        Field field = fieldList.stream()
                               .filter((f -> f.getName().equals(fieldName)))
                               .findFirst()
                               .orElse(null);
        if (field == null) {
            throw new NoSuchFieldException(fieldName);
        }
        field.setAccessible(true);
        return (T) field.get(obj);
    }

    private List<String> getUcpSearchPathFromUrlClassLoader(URLClassLoader classLoader) throws
            NoSuchFieldException,
            IllegalAccessException {
        URLClassPath ucp = getFieldFromObject(
                classLoader,
                "ucp",
                URLClassPath.class
        );
        List pathList = getFieldFromObject(ucp, "path", List.class);

        List<String> result = new ArrayList<>();
        for (Object o : pathList) {
            URL path = (URL) o;
            result.add(path.getPath());
        }
        return result;
    }

    @Override
    public List<String> getSearchPath() throws
            NoSuchFieldException,
            IllegalAccessException,
            IOException {
        ClassLoader appClassLoader =
                SystemSearchableResource.class.getClassLoader();
        List<ClassLoader> classLoaderList = new ArrayList<>();
        classLoaderList.add(appClassLoader);
        ClassLoader classLoader = appClassLoader;
        while (classLoader != null) {
            classLoaderList.add(classLoader);
            classLoader = classLoader.getParent();
        }
        List<String> ucpSearchPathFromUrlClassLoader;

        List<String> result = new ArrayList<>();

        for (int i = 0; i < classLoaderList.size(); i++) {
            classLoader = classLoaderList.get(i);
            ucpSearchPathFromUrlClassLoader =
                    getUcpSearchPathFromUrlClassLoader(
                            (URLClassLoader) classLoader);
            result.addAll(ucpSearchPathFromUrlClassLoader);
        }

        removeDuplicationByTreeSet(result);
        return result;
    }

    @Override
    public InputStream getResourceStream(String resourceName) {
        return this.getClass()
                   .getClassLoader()
                   .getResourceAsStream(resourceName);
    }

    /**
     * Read first level of resourceLocation
     *
     * @param resourceLocation Directory to search
     * @return Names of all files in the first level of resourceLocation; If
     * file is a directory, it must end with "/"; If the file is a regular file,
     * it must not end with "/".
     */
    private List<String> readFromDirectory(String resourceLocation) {
        File file = new File(resourceLocation);
        File[] files = file.listFiles();

        List<String> names = new ArrayList<>();
        if (files != null) {
            for (File f : files) {
                String name;
                name = f.getName();
                // add "/" to each directory name
                if (f.isDirectory()) {
                    name = name + "/";
                }
                names.add(name);
            }
        }

        return names;
    }

    private boolean isJarFile(String name) {
        if (name == null) {
            return false;
        }
        return name.endsWith(".jar");
    }

    /**
     * <p>
     * JarInputStream can only be directly created like below:
     * </p>
     * <pre>
     *     JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
     * </pre>
     * <p>
     * Code above wraps a FileInputStream with a JarInputStream, so we can read
     * JarEntry from the jar file conveniently.
     * </p>
     * <p>
     * But the problem is regular FileInputStream only accepts regular file path
     * but not nested jar path, like: /home/projects/java/demo2/target/demo2
     * .jar!/BOOT-INF/lib/property-resolver-1.0-SNAPSHOT.jar,
     * so we need to get the raw InputStream in a special way.
     * </p>
     * <p>
     * We can use ClassLoader::getResourceAsStream to get the jar inputstream.
     * </p>
     * <p>
     * This method use
     *
     * @param jarPath
     * @return
     * @throws IOException
     */
    public JarInputStream getNestedJarInputStream(String jarPath) throws
            IOException {
        InputStream resourceAsStream = this.getClass()
                                           .getClassLoader()
                                           .getResourceAsStream(jarPath);
        if (resourceAsStream != null) {
            return new JarInputStream(resourceAsStream);
        }
        return null;
    }

    private List<String> readFromJarFile(
            String basePackJarPathString,
            String dirName
    ) throws IOException {
        JarInputStream jarIn;
        if (isNestedJar(basePackJarPathString)) {
            String lastNestedJarRelativePath = getLastNestedJarRelativePath(
                    basePackJarPathString);
            jarIn = getNestedJarInputStream(lastNestedJarRelativePath);
        }
        else {
            jarIn = new JarInputStream(new FileInputStream(basePackJarPathString));
        }

        if (jarIn == null) {
            return new ArrayList<>();
        }

        JarEntry entry = jarIn.getNextJarEntry();

        List<String> nameList = new ArrayList<>();
        while (null != entry) {

            String name = entry.getName();
            if (name.contains(dirName)) {
                nameList.add(name);
            }

            entry = jarIn.getNextJarEntry();
        }

        return nameList;
    }

    @Override
    public List<String> listResourceAt(String dirName) throws IOException {

        // dirName: com/example/

        // result
        List<String> resultNames = new ArrayList<>();

        // find dir through url classloader
        URL dirResourceUrl = this.getClass()
                                 .getClassLoader()
                                 .getResource(dirName);

        // if dir is found in a system path, resourceLocation is the absolute
        // path of the dir
        // /test/path/com/example for example
        // if dir is found in a jar file, resourceLocation is the location of
        // the jar file(could be nested)
        // jar:file:/test/path/jarfile.jar!/com/example for example
        String resourceLocation = getPackPath(dirResourceUrl);


        boolean isJar = isJarFile(resourceLocation);
        if (isJar) {
            resultNames = readFromJarFile(resourceLocation, dirName);
            resultNames.remove(dirName);
            return resultNames;
        }

        // resource is in a system path, read system directory recursively

        // only name of all files in the resourceLocation
        List<String> middleNames = null;
        middleNames = readFromDirectory(resourceLocation);

        // add dirName path prefix for all names
        List<String> tempList = new ArrayList<>();
        for (String name : middleNames) {
            tempList.add(dirName + name);
        }
        middleNames = tempList;

        // read recursively
        for (String name : middleNames) {
            // name: com/example/test.class for example
            if (!isDir(name)) {
                //nameList.add(basePackage + "." + StringUtil.trimExtension(name));
                resultNames.add(name);
            }
            else {
                // name: com/example/nestdir/ for example

                // get the dir name
                name = name.substring(
                        0,
                        name.length() - 1
                ); // remove dir suffix "/"
                int i = name.lastIndexOf("/");
                if (i != -1) {
                    name = name.substring(i + 1);
                }
                // name: nestdir
                // arg: com/example/nestdir/
                resultNames.addAll(listResourceAt(dirName + name + "/"));
            }
        }

        // remove com/example/ from results
        resultNames.remove(dirName);
        return resultNames;
    }

    private boolean isDir(String name) {
        return name.endsWith("/");
    }
}
