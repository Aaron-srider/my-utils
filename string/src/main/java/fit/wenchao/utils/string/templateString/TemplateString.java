package fit.wenchao.utils.string.templateString;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.*;

public class TemplateString {

    public static final String INT = "int";
    public static final String BYTE = "byte";
    public static final String SHOT = "short";
    public static final String LONG = "long";
    public static final String FLOAT = "float";
    public static final String DOUBLE = "double";
    public static final String CHAR = "char";
    public static final String BOOL = "boolean";
    static final int SINGLE_QUOTE = 39;

    public static void main(String[] args) {
        System.out.println(ft("hello: {} :{}", "world", "world"));

        Integer[] arr = new Integer[10];
        int[] arr1 = new int[10];

        System.out.println(ft("hello: {}", arr(arr)));
    }

    public static String ft(String format, Object... args) {
        return new TemplateString().formatString(format, args);
    }

    public static ArrayWrapper arr(Object arr) {
        Object[] objects = convertToObjectArray(arr);
        return new ArrayWrapper(objects);
    }

    private static Object[] convertToObjectArray(Object unknownArr) {
        Class aClass = unknownArr.getClass();
        Object[] objects = null;
        if (aClass.isArray()) {
            String arrayElemTypeName = aClass.getComponentType().toString();
            switch (arrayElemTypeName) {
                case INT: {
                    int[] arr = (int[]) unknownArr;
                    objects = new Object[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        objects[i] = arr[i];
                    }
                    break;
                }
                case BYTE: {
                    byte[] arr = (byte[]) unknownArr;
                    objects = new Object[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        objects[i] = arr[i];
                    }
                    break;
                }


                case SHOT: {
                    short[] arr = (short[]) unknownArr;
                    objects = new Object[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        objects[i] = arr[i];
                    }
                    break;
                }
                case LONG: {
                    long[] arr = (long[]) unknownArr;
                    objects = new Object[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        objects[i] = arr[i];
                    }
                    break;
                }
                case FLOAT: {
                    float[] arr = (float[]) unknownArr;
                    objects = new Object[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        objects[i] = arr[i];
                    }
                    break;
                }
                case DOUBLE: {
                    double[] arr = (double[]) unknownArr;
                    objects = new Object[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        objects[i] = arr[i];
                    }
                    break;
                }
                case CHAR: {
                    char[] arr = (char[]) unknownArr;
                    objects = new Object[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        objects[i] = arr[i];
                    }
                    break;
                }
                case BOOL: {
                    boolean[] arr = (boolean[]) unknownArr;
                    objects = new Object[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        objects[i] = arr[i];
                    }
                    break;
                }
                default:
                    objects = (Object[]) unknownArr;
            }


        }
        return objects;
    }

    public static ArrayWrapper arr(int[] arr) {
        Object[] objects = new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            objects[i] = arr[i];
        }
        return new ArrayWrapper(objects);
    }

    private static void findAndStorePairQuotes(int currentIdx, String format, Map<Integer, Integer> singleQuotesIndexMap, List<Entry> singleQuotesEntryList) {
        if (SINGLE_QUOTE == format.charAt(currentIdx)) {
            if (!singleQuotesIndexMap.containsKey(currentIdx) && !singleQuotesIndexMap.containsValue(currentIdx)) {
                boolean ifFoundAnotherSingleQuoteAfterExistsOne = false;

                for (int anotherSingleQuoteIndex = currentIdx + 1; anotherSingleQuoteIndex < format.length(); anotherSingleQuoteIndex++) {
                    if (format.charAt(anotherSingleQuoteIndex) == SINGLE_QUOTE) {
                        ifFoundAnotherSingleQuoteAfterExistsOne = true;
                        singleQuotesIndexMap.put(currentIdx, anotherSingleQuoteIndex);
                        singleQuotesEntryList.add(new Entry(currentIdx, anotherSingleQuoteIndex));
                        break;
                    }

                }

                if (!ifFoundAnotherSingleQuoteAfterExistsOne) {
                    //没有匹配的单引号，用null表示
                    singleQuotesEntryList.add(new Entry(currentIdx, null));
                    singleQuotesIndexMap.put(currentIdx, null);
                }

            }
        }
    }

    private static boolean ifCurCharBetweenQuotes(int idx, List<Entry> quotes) {

        if (quotes.isEmpty()) {
            return false;
        }

        Entry lastEntry = quotes.get(quotes.size() - 1);

        return idx > lastEntry.getK() &&
                (lastEntry.getV() == null || idx + 1 < lastEntry.getV());
    }

    public static String[] transEachElemToString(Object[] args) {
        String[] stringDescForArgs = new String[args.length];
        for (int idx = 0; idx < args.length; idx++) {
            Object itm = args[idx];
            String s = null;
            if (itm == null) {
                s = "null";
            }
            else if (itm.getClass().isArray()) {
                s = convertArrayToString(itm);
            }
            else {
                s = itm.toString();
            }
            //args[idx] = s;
            stringDescForArgs[idx] = s;
        }
        return stringDescForArgs;

    }

    private static String convertArrayToString(Object arg) {
        String s = null;
        if (arg instanceof long[]) {
            s = Arrays.toString((long[]) arg);
        }
        else if (arg instanceof int[]) {
            s = Arrays.toString((int[]) arg);
        }
        else if (arg instanceof short[]) {
            s = Arrays.toString((short[]) arg);
        }
        else if (arg instanceof char[]) {
            s = Arrays.toString((char[]) arg);
        }
        else if (arg instanceof byte[]) {
            s = Arrays.toString((byte[]) arg);
        }
        else if (arg instanceof boolean[]) {
            s = Arrays.toString((boolean[]) arg);
        }
        else if (arg instanceof float[]) {
            s = Arrays.toString((float[]) arg);
        }
        else if (arg instanceof double[]) {
            s = Arrays.toString((double[]) arg);
        }
        else if (arg instanceof Object[]) {
            s = Arrays.toString((Object[]) arg);
        }
        return s;
    }

    @Test
    public void test_convertToObjectArray() {
        Object ints = new Object[2];

        Object[] objects = convertToObjectArray(ints);
        Arrays.stream(objects).forEach(System.out::println);
    }

    public String formatString(String format, Object... args) {

        //将format字符串转换成messageFormat能处理的格式
        String formatStrCanBeProcessedByMessageFormat = transRowFormatStrToTheOneCanBeProcessedByMsgFormat(format);

        unpackArrayWrapper(args);

        //将所有参数替换成String，包括数组
        String[] stringDescForArgs = transEachElemToString(args);

        return doFormatStringWithPlaceholder(formatStrCanBeProcessedByMessageFormat, stringDescForArgs);
    }

    private void unpackArrayWrapper(Object[] args) {
        for (int i = 0; i < args.length; i++) {
            Object e = args[i];
            if (e instanceof ArrayWrapper) {
                Object[] arr = ((ArrayWrapper) e).getArr();
                args[i] = arr;
            }
        }
    }

    private String doFormatStringWithPlaceholder(String formatCanBeProcessedByMsgFormat, Object... args) {
        MessageFormat messageFormat = new MessageFormat(formatCanBeProcessedByMsgFormat);
        String rst = messageFormat.format(args);
        return rst;
    }

    public String transRowFormatStrToTheOneCanBeProcessedByMsgFormat(String rowFormat) {
        if (rowFormat == null) {
            return "null";
        }
        StringBuilder formatCanBeProcessedByMsgFormat = new StringBuilder("");

        List<Entry> singleQuotesEntryList = new ArrayList<>();

        Map<Integer, Integer> singleQuotesIndexMap = new HashMap<>();

        int countOfBraces = 0;

        for (int i = 0; i < rowFormat.length(); i++) {
            int idx = i;
            char curChar = rowFormat.charAt(i);

            //如果当前的字符是单引号，则找到与之匹配的下一个，并将这对单引号存储起来
            findAndStorePairQuotes(idx, rowFormat, singleQuotesIndexMap, singleQuotesEntryList);
            //如果找到{}，则在中间填写序号count
            if ('{' == curChar && '}' == rowFormat.charAt(idx + 1)) {
                if (!ifCurCharBetweenQuotes(idx, singleQuotesEntryList)) {
                    formatCanBeProcessedByMsgFormat.append("{");
                    formatCanBeProcessedByMsgFormat.append(countOfBraces++);
                }
                else {
                    formatCanBeProcessedByMsgFormat.append("{");
                }
            }
            else {
                formatCanBeProcessedByMsgFormat.append(curChar);
            }
        }
        return formatCanBeProcessedByMsgFormat.toString();
    }

    private static class ArrayWrapper {

        Object[] arr;

        private ArrayWrapper(Object[] arr) {
            this.arr = arr;
        }

        public Object[] getArr() {
            return arr;
        }
    }

    @Data
    @AllArgsConstructor
    static class Entry {
        Integer k;
        Integer v;
    }
}
