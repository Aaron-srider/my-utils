package fit.wenchao.utils.path;

import fit.wenchao.utils.path.pathBuilder.FilePathBuilder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PathUtils {

    public static String trimTailSeparator(String path) {
        if (path == null) {
            return null;
        }
        if (path.endsWith(File.separator)) {
            while (path.endsWith(File.separator)) {
                path = path.substring(0, path.length() - 1);
            }
            return path;
        }
        return path;
    }

    public static String trimHeadSeparator(String path) {
        if (path == null) {
            return null;
        }
        if (path.startsWith(File.separator)) {
            while (path.startsWith(File.separator)) {
                path = path.substring(1);
            }
            return path;
        }
        return path;
    }

    public static FilePathBuilder filePathBuilder(String initPathStr) {
        return new FilePathBuilder(initPathStr);
    }

    public static FilePathBuilder filePathBuilder() {
        return filePathBuilder("");
    }

    public static List<String> splitPathStringToNodes(String path) {

        path = trimTailSeparator(path);

        path = trimHeadSeparator(path);

        if ("".equals(path)) {
            return Collections.emptyList();
        }

        if (!path.contains(File.separator)) {
            return Arrays.asList(new String[]{path});
        }


        String[] split = path.split(File.separator);
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < split.length; i++) {
            String node = split[i];
            result.add(node);
        }

        return result;
    }

    private static String trimSeparator(String path) {
        path = trimTailSeparator(path);
        path = trimHeadSeparator(path);
        return path;
    }

    public static String resolveFileName(String path) {
        path = trimSeparator(path);

        int lastIndexOfSplash = path.lastIndexOf("/");

        if (lastIndexOfSplash == -1) {
            return path;
        }
        return path.substring(lastIndexOfSplash + 1);
    }

    public static String relativeToDir(String dirPath, String filePath) {
        Path relativePath =
                Paths.get(dirPath).relativize(Paths.get(filePath));

        return relativePath.toString();
    }


    public static void main(String[] args) {
        System.out.println(relativeToDir("/", "/home"));
        List<String> strings = splitPathStringToNodes("/test/home/dir");
        System.out.println(strings);
        strings = splitPathStringToNodes("test/home/dir");
        System.out.println(strings);
        strings = splitPathStringToNodes("test/home/dir/");
        System.out.println(strings);
        strings = splitPathStringToNodes("/test/home/dir/");
        System.out.println(strings);

        strings = splitPathStringToNodes("test");
        System.out.println(strings);
        strings = splitPathStringToNodes("/test");
        System.out.println(strings);
        strings = splitPathStringToNodes("/test/");
        System.out.println(strings);
        strings = splitPathStringToNodes("test/");
        System.out.println(strings);

        String path = filePathBuilder().ct("home")
                                       .ct("/cw")
                                       .ct("\\test")
                                       .ct("dir/")
                                       .build();
        System.out.println(path);

        String filename = resolveFileName("/home/cw");
        System.out.println(filename);
        filename = resolveFileName("home");
        System.out.println(filename);
        filename = resolveFileName("home/cw");
        System.out.println(filename);
    }


}
