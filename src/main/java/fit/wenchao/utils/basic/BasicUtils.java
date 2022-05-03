package fit.wenchao.utils.basic;

import fit.wenchao.utils.collection.MapService;
import fit.wenchao.utils.collection.MyArrayList;
import fit.wenchao.utils.function.*;

import java.util.*;

import static fit.wenchao.utils.collection.MyArrayList.asList;
public class BasicUtils {

    /**
     * while循环
     *
     * @param voidFunction 循环体
     * @throws Exception 循环体函数抛出的异常
     */
    public static void loop(VoidFunction voidFunction) throws Exception {
        while (true) {
            voidFunction.apply();
        }
    }

    /**
     * 执行指定的操作lp_count次
     *
     * @param lp_count     要执行的次数
     * @param voidFunction lambda，不接收任何参数
     * @throws Exception 循环体函数抛出的异常
     */
    @Deprecated
    public static void loop(int lp_count, VoidFunction voidFunction) throws Exception {
        for (int i = 0; i < lp_count; i++) {
            voidFunction.apply();
        }
    }


    public static class LoopState {

        private static final String NONE = "none";
        private static final String NORMAL = "normal";
        private static final String CONTINUE = "continue";
        private static final String BREAK = "break";

        String state = NONE;

        public void reset() {
            this.state = NONE;
        }


        public boolean emptyState() {
            return this.state.equals(NONE);
        }

        public void normal() {
            this.state = NORMAL;
        }

        public boolean isContinue() {
            return this.state.equals(CONTINUE);
        }

        public void continueLoop() {
            this.state = CONTINUE;
        }

        public void breakLoop() {
            this.state = BREAK;
        }

        public boolean isBreak() {
            return this.state.equals(BREAK);
        }
    }

    public static class Flag {
        boolean flag;

        public boolean get() {
            return flag;
        }

        public void set(boolean flag) {
            this.flag = flag;
        }
    }

    @Deprecated
    private static interface SequenceAdapter {
        Object get(Object targetSeq, int index);

        boolean supportSequence(Object seq);

        int size(Object targetSeq);
    }

    @Deprecated
    private static class ArraySeqAdapter implements SequenceAdapter {

        ArraySeqAdapter() {

        }

        @Override
        public Object get(Object targetSeq, int index) {
            return ((Object[]) targetSeq)[index];
        }

        @Override
        public boolean supportSequence(Object seq) {
            if (seq.getClass().isArray()) {
                return true;
            }
            return false;
        }

        @Override
        public int size(Object targetSeq) {
            return ((Object[]) targetSeq).length;
        }
    }

    @Deprecated
    private static class ListSeqAdapter implements SequenceAdapter {

        ListSeqAdapter() {
        }

        @Override
        public Object get(Object targetSeq, int index) {
            return ((List) targetSeq).get(index);
        }

        @Override
        public boolean supportSequence(Object seq) {
            if (List.class.isAssignableFrom(seq.getClass())) {
                return true;
            }
            return false;
        }

        @Override
        public int size(Object targetSeq) {
            return ((List) targetSeq).size();
        }
    }

    @Deprecated
    private static class StringSeqAdapter implements SequenceAdapter {

        StringSeqAdapter() {
        }

        @Override
        public Character get(Object targetSeq, int index) {
            return ((String) targetSeq).charAt(index);
        }

        @Override
        public boolean supportSequence(Object seq) {
            if (seq.getClass().equals(String.class)) {
                return true;
            }
            return false;
        }

        @Override
        public int size(Object targetSeq) {
            return ((String) targetSeq).length();
        }
    }

    @Deprecated
    private static class IntegerSeqAdapter implements SequenceAdapter {

        IntegerSeqAdapter() {
        }

        @Override
        public Integer get(Object targetSeq, int index) {
            return index;
        }

        @Override
        public boolean supportSequence(Object seq) {
            if (seq.getClass().equals(Integer.class)) {
                return true;
            }
            return false;
        }

        @Override
        public int size(Object targetSeq) {
            return (Integer) targetSeq;
        }
    }


    private static interface GenericSequenceAdapter<T> {
        T get(int index);

        int size();
    }


    private static class GenericArraySeqAdapter<T> implements GenericSequenceAdapter<T> {

        T[] array;

        GenericArraySeqAdapter(T[] array) {
            this.array = array;
        }

        @Override
        public T get(int index) {
            return array[index];
        }

        @Override
        public int size() {
            return array.length;
        }
    }

    private static class GenericListSeqAdapter<T> implements GenericSequenceAdapter<T> {

        List<T> list;

        GenericListSeqAdapter(List<T> list) {
            this.list = list;
        }

        @Override
        public T get(int index) {
            return list.get(index);
        }

        @Override
        public int size() {
            return list.size();
        }
    }

    private static class GenericStringSeqAdapter<T> implements GenericSequenceAdapter<T> {

        String string;

        GenericStringSeqAdapter(String string) {
            this.string = string;
        }

        @Override
        public T get(int index) {
            return (T) ((Character) string.charAt(index));
        }

        @Override
        public int size() {
            return string.length();
        }
    }

    private static class GenericIntegerSeqAdapter<T> implements GenericSequenceAdapter<T> {

        Integer integer;

        GenericIntegerSeqAdapter(Integer integer) {
            this.integer = integer;
        }

        @Override
        public T get(int index) {
            if (index >= integer) {
                throw new ArrayIndexOutOfBoundsException(index);
            }
            return (T) (Integer) index;
        }

        @Override
        public int size() {
            return integer;
        }
    }

    private static List<SequenceAdapter> sequenceAdapters;

    static {
        sequenceAdapters = new ArrayList<>();
        sequenceAdapters.add(new ArraySeqAdapter());
        sequenceAdapters.add(new StringSeqAdapter());
        sequenceAdapters.add(new ListSeqAdapter());
        sequenceAdapters.add(new IntegerSeqAdapter());
    }


    /**
     * 遍历常数
     *
     * @param lp_count 要遍历的遍数
     * @param consumer lambda，接收当前index
     * @throws Exception 循环体函数抛出的异常
     */
    @Deprecated
    public static void loop(int lp_count, ExceptionConsumer<Integer> consumer) throws Exception {
        SequenceAdapter sequenceAdapter = null;
        sequenceAdapter = getAdapter(lp_count);
        for (int i = 0; i < sequenceAdapter.size(lp_count); i++) {
            consumer.accept(i);
        }
    }

    /**
     * 遍历有序序列，包括数组，列表和字符串
     *
     * @param targetSeq  要遍历的有序序列
     * @param biConsumer lambda，接收当前index、当前item
     */
    //@Deprecated
    //public static void loop(Object targetSeq, ExceptionBiConsumer<Integer, Object> biConsumer) throws Exception {
    //    SequenceAdapter sequenceAdapter = null;
    //    sequenceAdapter = getAdapter(targetSeq);
    //    for (int i = 0; i < sequenceAdapter.size(targetSeq); i++) {
    //        biConsumer.accept(i, sequenceAdapter.get(targetSeq, i));
    //    }
    //}

    /**
     * 遍历常数
     *
     * @param lp_count   要遍历的遍数
     * @param biConsumer lambda，接收当前index，和循环控制变量loopState
     * @throws Exception 循环体函数抛出的异常
     */
    @Deprecated
    public static void loop(int lp_count, ExceptionBiConsumer<Integer, LoopState> biConsumer) throws Exception {
        SequenceAdapter sequenceAdapter = null;
        sequenceAdapter = getAdapter(lp_count);

        LoopState loopState = new LoopState();
        for (int i = 0; i < sequenceAdapter.size(lp_count); i++) {
            loopState.reset();
            biConsumer.accept((Integer) sequenceAdapter.get(lp_count, i), loopState);
            if (loopState.emptyState()) {
                loopState.normal();
            }
            if (loopState.isContinue()) {
                continue;
            }
            if (loopState.isBreak()) {
                break;
            }
        }
    }

    /**
     * 遍历常数
     *
     * @param lp_count   要遍历的遍数
     * @param trConsumer lambda，接收当前index、循环控制变量loopState，和一个标志位flag
     * @return 返回标志位flag的值
     * @throws Exception 循环体函数抛出的异常
     */
    @Deprecated
    public static boolean loop(int lp_count, ExceptionTriConsumer<Integer, LoopState, Flag> trConsumer) throws Exception {
        SequenceAdapter sequenceAdapter = null;
        sequenceAdapter = getAdapter(lp_count);

        LoopState loopState = new LoopState();
        Flag loopFlag = new Flag();
        for (int i = 0; i < sequenceAdapter.size(lp_count); i++) {
            loopState.reset();
            trConsumer.accept((Integer) sequenceAdapter.get(lp_count, i), loopState, loopFlag);
            if (loopState.emptyState()) {
                loopState.normal();
            }
            if (loopState.isContinue()) {
                continue;
            }
            if (loopState.isBreak()) {
                break;
            }
        }
        return loopFlag.get();
    }

    @Deprecated
    private static SequenceAdapter getAdapter(Object targetSeq) {
        SequenceAdapter sequenceAdapter = null;
        for (int i = 0; i < sequenceAdapters.size(); i++) {
            if (sequenceAdapters.get(i).supportSequence(targetSeq)) {
                sequenceAdapter = sequenceAdapters.get(i);
            }
        }
        return sequenceAdapter;
    }


    /**
     * 遍历有序序列，包括数组，列表和字符串
     *
     * @param targetSeq  要遍历的有序序列
     * @param trConsumer lambda，接收当前index、当前item和循环控制变量loopState
     */
    //@Deprecated
    //public static void loop(Object targetSeq, ExceptionTriConsumer<Integer, Object, LoopState> trConsumer) throws Exception {
    //    SequenceAdapter sequenceAdapter = null;
    //    sequenceAdapter = getAdapter(targetSeq);
    //
    //    LoopState loopState = new LoopState();
    //    for (int i = 0; i < sequenceAdapter.size(targetSeq); i++) {
    //        loopState.reset();
    //        trConsumer.accept(i, sequenceAdapter.get(targetSeq, i), loopState);
    //        if (loopState.emptyState()) {
    //            loopState.normal();
    //        }
    //        if (loopState.isContinue()) {
    //            continue;
    //        }
    //        if (loopState.isBreak()) {
    //            break;
    //        }
    //    }
    //}

    /**
     * 遍历有序序列，包括数组，列表和字符串
     *
     * @param targetSeq    要遍历的有序序列
     * @param quadConsumer lambda，接收当前index、当前item和循环控制变量loopState，以及标志位loopFlag
     */
    //@Deprecated
    //public static boolean loop(Object targetSeq, ExceptionQuadConsumer<Integer, Object, LoopState, Flag> quadConsumer) throws Exception {
    //    SequenceAdapter sequenceAdapter = null;
    //    sequenceAdapter = getAdapter(targetSeq);
    //
    //    Flag loopFlag = new Flag();
    //    LoopState loopState = new LoopState();
    //    for (int i = 0; i < sequenceAdapter.size(targetSeq); i++) {
    //        loopState.reset();
    //        quadConsumer.accept(i, sequenceAdapter.get(targetSeq, i), loopState, loopFlag);
    //        if (loopState.emptyState()) {
    //            loopState.normal();
    //        }
    //        if (loopState.isContinue()) {
    //            continue;
    //        }
    //        if (loopState.isBreak()) {
    //            break;
    //        }
    //    }
    //    return loopFlag.get();
    //}


    /**
     * 无序遍历map
     *
     * @param map         要遍历的map
     * @param triConsumer lambda，接收当前entry、当前entry的key、当前entry的value
     * @param <K>         Map的key类型
     * @param <V>         Map的value类型
     * @throws Exception 循环体函数抛出的异常
     */
    @Deprecated
    public static <K, V> void loop(Map<K, V> map, ExceptionTriConsumer<Map.Entry<K, V>, K, V> triConsumer) throws Exception {
        Set<Map.Entry<K, V>> entries = map.entrySet();
        for (Map.Entry<K, V> entry : entries) {
            triConsumer.accept(entry, entry.getKey(), entry.getValue());
        }
    }

    @Deprecated
    public static <T> void gloop(GenericSequenceAdapter<T> seqAdapter,
                                 ExceptionTriConsumer<Integer, T, LoopState> trConsumer) throws Exception {
        LoopState loopState = new LoopState();
        for (int i = 0; i < seqAdapter.size(); i++) {
            loopState.reset();
            trConsumer.accept(i, seqAdapter.get(i), loopState);
            if (loopState.emptyState()) {
                loopState.normal();
            }
            if (loopState.isContinue()) {
                continue;
            }
            if (loopState.isBreak()) {
                break;
            }
        }
    }

    @Deprecated
    public static <T> boolean gloop(GenericSequenceAdapter<T> seqAdapter,
                                    ExceptionQuadConsumer<Integer, T, LoopState, Flag> quadConsumer) throws Exception {
        Flag loopFlag = new Flag();
        LoopState loopState = new LoopState();
        for (int i = 0; i < seqAdapter.size(); i++) {
            loopState.reset();
            quadConsumer.accept(i, seqAdapter.get(i), loopState, loopFlag);
            if (loopState.emptyState()) {
                loopState.normal();
            }
            if (loopState.isContinue()) {
                continue;
            }
            if (loopState.isBreak()) {
                break;
            }
        }
        return loopFlag.get();
    }


    public static void main(String[] args) throws Exception {

        Map<String, String> map = MapService.of("name", "vallue",
                "age", "11");
        hloop(map(map), (idx, entry, s) -> {
            if (entry.getKey().equals("name")) {
                s.continueLoop();
            } else {
                System.out.println(entry);
            }
        });

        MyArrayList<String> strings = asList("1", "2", "3");

        hloop(list(strings), (idx, item, s) -> {
            if (item.equals("2")) {
                s.continueLoop();
            } else {
                System.out.println(item);
            }
        });

        hloop(str("123"), (idx, c, s) -> {
            if (c.equals('2')) {
                s.continueLoop();
            } else {
                System.out.println(c);
            }
        });

        Enumeration<String> days;
        Vector<String> dayNames = new Vector<String>();
        dayNames.add("Sunday");
        dayNames.add("Monday");
        dayNames.add("Tuesday");
        dayNames.add("Wednesday");
        dayNames.add("Thursday");
        dayNames.add("Friday");
        dayNames.add("Saturday");
        days = dayNames.elements();

        hloop(enumeration(days), (idx, item, s) -> {
            System.out.println(item);
        });
    }


    interface IteratorAdaptor<T> {
        Iterator<T> getIterator();
    }

    static class MapIteratorAdaptor<K, V> implements IteratorAdaptor<Map.Entry<K, V>> {
        Map<K, V> map;

        public MapIteratorAdaptor(Map<K, V> map) {
            this.map = map;
        }

        public Iterator<Map.Entry<K, V>> getIterator() {
            return map.entrySet().iterator();
        }
    }

    static class ListIteratorAdaptor<T> implements IteratorAdaptor<T> {
        List<T> list;

        public ListIteratorAdaptor(List<T> list) {
            this.list = list;
        }

        public Iterator<T> getIterator() {
            return list.iterator();
        }
    }

    static class StringIterator implements Iterator<Character> {
        Integer currentIndex = -1;
        String string;

        public StringIterator(String string) {
            this.string = string;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < string.length() - 1;
        }

        @Override
        public Character next() {
            char c = string.charAt(currentIndex + 1);
            currentIndex++;
            return c;
        }
    }

    static class StringIteratorAdaptor implements IteratorAdaptor<Character> {
        String string;

        public StringIteratorAdaptor(String string) {
            this.string = string;
        }

        public Iterator<Character> getIterator() {
            return new StringIterator(this.string);
        }
    }


    static class EnumerationIterator<T> implements Iterator<T> {
        Enumeration<T> enumeration;

        public EnumerationIterator(Enumeration<T> enumeration) {
            this.enumeration = enumeration;
        }

        @Override
        public boolean hasNext() {
            return enumeration.hasMoreElements();
        }

        @Override
        public T next() {
            return enumeration.nextElement();
        }
    }

    static class EnumerationIteratorAdaptor<T> implements IteratorAdaptor<T> {
        Enumeration<T> enumeration;

        public EnumerationIteratorAdaptor(Enumeration<T> enumeration) {
            this.enumeration = enumeration;
        }

        public Iterator<T> getIterator() {
            return new EnumerationIterator<>(enumeration);
        }
    }


    public static <T> IteratorAdaptor<T> list(List<T> list) {
        return new ListIteratorAdaptor<>(list);
    }

    public static IteratorAdaptor<Character> str(String string) {
        return new StringIteratorAdaptor(string);
    }

    public static <K, V> MapIteratorAdaptor<K, V> map(Map<K, V> map) {
        return new MapIteratorAdaptor<>(map);
    }

    public static <T> EnumerationIteratorAdaptor<T> enumeration(Enumeration<T> enumeration) {
        return new EnumerationIteratorAdaptor<>(enumeration);
    }


    public static <T> void hloop(IteratorAdaptor<T> iteratorAdaptor,
                                 ExceptionTriConsumer<Integer, T, LoopState> quadConsumer) throws Exception {
        LoopState loopState = new LoopState();
        Iterator<T> iterator = iteratorAdaptor.getIterator();
        int count = 0;
        while (iterator.hasNext()) {
            loopState.reset();
            T next = iterator.next();
            quadConsumer.accept(count++, next,
                    loopState);
            if (loopState.emptyState()) {
                loopState.normal();
            }
            if (loopState.isContinue()) {
                continue;
            }
            if (loopState.isBreak()) {
                break;
            }
        }

    }

    @Deprecated
    public static <T> void gloop(Enumeration<T> enumeration,
                                 ExceptionTriConsumer<T, LoopState, Boolean> quadConsumer) throws Exception {
        LoopState loopState = new LoopState();
        boolean hasNext;
        while (enumeration.hasMoreElements()) {
            T t = enumeration.nextElement();
            hasNext = enumeration.hasMoreElements();
            quadConsumer.accept(t, loopState, hasNext);
            if (loopState.emptyState()) {
                loopState.normal();
            }
            if (loopState.isContinue()) {
                continue;
            }
            if (loopState.isBreak()) {
                break;
            }
        }
    }

    @Deprecated
    public static <T> boolean gloop(Enumeration<T> enumeration,
                                    ExceptionQuadConsumer<T, LoopState, Flag, Boolean> quadConsumer) throws Exception {
        Flag loopFlag = new Flag();
        LoopState loopState = new LoopState();

        boolean hasNext;
        while ((hasNext = enumeration.hasMoreElements())) {
            T t = enumeration.nextElement();
            quadConsumer.accept(t, loopState, loopFlag, hasNext);
            if (loopState.emptyState()) {
                loopState.normal();
            }
            if (loopState.isContinue()) {
                continue;
            }
            if (loopState.isBreak()) {
                break;
            }
        }
        return loopFlag.get();
    }

    @Deprecated
    public static <T> GenericListSeqAdapter<T> forList(List<T> list) {
        return new GenericListSeqAdapter<>(list);
    }

    @Deprecated
    public static <T> GenericArraySeqAdapter<T> forArr(T[] arr) {
        return new GenericArraySeqAdapter<>(arr);
    }

    @Deprecated
    public static GenericStringSeqAdapter<Character> forStr(String string) {
        return new GenericStringSeqAdapter<>(string);
    }

    @Deprecated
    public static GenericIntegerSeqAdapter<Integer> forInt(Integer integer) {
        return new GenericIntegerSeqAdapter<>(integer);
    }
}
