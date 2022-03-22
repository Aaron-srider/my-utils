package fit.wenchao.utils.collection;

import java.util.*;

public class MyArrayList<E> extends ArrayList<E> {
    public E last() {
        return this.get(this.size() - 1);
    }

    public MyArrayList(Collection<? extends E> c) {
        super(c);
    }

    public MyArrayList() {

    }

    @SafeVarargs
    public static <T> MyArrayList<T> asList(T... a) {
        List<T> ts = Arrays.asList(a);
        return new MyArrayList<>(ts);
    }

    public boolean isLastIdx(int i) {
        return size() - 1 == i;
    }

    public static <T> MyArrayList<T> getDefaultList(Class<T> elemClazz) {
        return new MyArrayList<>();
    }
}
