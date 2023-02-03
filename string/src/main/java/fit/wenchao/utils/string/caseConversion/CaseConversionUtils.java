package fit.wenchao.utils.string.caseConversion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Provide tools for var name conversion
public class CaseConversionUtils {

    // helloWorld -> hello-world
    public static String lowerCamel2LowerHyphen(String name) {
        String separator = "-";
        name = name.replaceAll("([a-z])([A-Z])", "$1" + separator + "$2")
                   .toLowerCase();
        return name;
    }

    // hello-world -> hello_world
    public static String lowerHyphen2LowerUnderScore(String name) {
        return name.replace('-', '_');
    }

    // helloWorld -> hello_world
    public static String lowerCamel2LowerUnderScore(String name) {
        String separator = "_";
        name = name.replaceAll("([a-z])([A-Z])", "$1" + separator + "$2")
                   .toLowerCase();
        return name;
    }

    // hello_world -> helloWorld
    public static String lowerUnderScore2LowerCamel(String name) {
        String separator = "-";
        String regex = "([a-z])_([a-z])";
        Pattern compile = Pattern.compile(regex);

        Matcher matcher = compile.matcher(name);

        while (matcher.find()) {
            String group = matcher.group();
            System.out.println(group);
            char c = group.charAt(2);
            String replaceGroup = group.replace("_" + c, "" + Character.toUpperCase(c));
            name = name.replace(group, replaceGroup);
        }

        return name;
    }


    public static void main(String[] args) {
        System.out.println(lowerCamel2LowerHyphen("helloWorld"));
        System.out.println(lowerCamel2LowerHyphen("hello"));

        System.out.println(lowerCamel2LowerUnderScore("helloWorld"));
        System.out.println(lowerCamel2LowerUnderScore("hello"));

        System.out.println(lowerHyphen2LowerUnderScore("hello-world"));
        System.out.println(lowerHyphen2LowerUnderScore("hello"));

        System.out.println(lowerUnderScore2LowerCamel("hello_world"));
        System.out.println(lowerUnderScore2LowerCamel("hello"));
    }
}
