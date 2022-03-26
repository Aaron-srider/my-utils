package fit.wenchao.utils.string;

import java.io.File;

public class StrUtils {

    public static String ft(String format, Object... args) {
        return new StringFormatter().formatString(format, args);
    }

    /**
     * 如果参数只有一个，并且是数组，请使用asList将其转换为List，如果是多个数组，可以不用转换。
     * @param format 格式化字符串，其中包含占位符<pre>"{}"</pre>，形式为：<pre>{@code "the result is:{}, expected is:{}"}</pre>
     * @param args 依次对应format模板中的占位符
     * @return 返回输出的字符串
     */
    public static String outf(String format, Object... args) {
        String resultString
                = new StringFormatter().formatString(format, args);
        System.out.println(resultString);
        return resultString;
    }

    public static class FilePathBuilder {
        StringBuilder sb;

        public FilePathBuilder() {
            this.sb =  new StringBuilder();
        }

        public FilePathBuilder(String initPathStr) {
            String systemSpecificPath = convertSeparatorOfPath(initPathStr);
            this.sb = new StringBuilder(systemSpecificPath);
        }

        public FilePathBuilder ct(String rowPath) {

            String systemSpecificPath = convertSeparatorOfPath(rowPath);

            if (!systemSpecificPath.startsWith(File.separator)) {
                sb.append(File.separator);
            }

            sb.append(systemSpecificPath);
            return this;
        }

        private String convertSeparatorOfPath(String path) {
            String systemString = System.getProperty("os.name").toLowerCase();
            if(systemString.contains("windows")) {
                path = path.replace("/", File.separator);
            } else if(systemString.contains("linux") || systemString.contains("mac")) {
                path = path.replace("\\", File.separator);
            }
            return path;
        }

        public String build() {
            return sb.toString();
        }
    }

    public static FilePathBuilder filePathBuilder(String initPathStr) {
        return new FilePathBuilder(initPathStr);
    }

    public static FilePathBuilder filePathBuilder() {
        return filePathBuilder("");
    }

    public static void main(String[] args) {
        String build = filePathBuilder("").ct("\\path\\com/example").ct("you").ct("hello").build();
        StrUtils.outf("path:{}",build);

        System.out.println(System.getProperty("os.name"));


    }



}