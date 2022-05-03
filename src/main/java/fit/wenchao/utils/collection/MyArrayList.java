package fit.wenchao.utils.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MyArrayList<E> extends ArrayList<E> {

    public MyArrayList() {

    }

    public MyArrayList(Collection<? extends E> c) {
        super(c);
    }

    // region extension
    public boolean isLastIdx(int i) {
        return size() - 1 == i;
    }

    public E last() {
        return this.get(this.size() - 1);
    }
    // endregion

    public static <T> MyArrayList<T> getInstance(Class<T> elemClazz) {
        return new MyArrayList<>();
    }

    public static <T> MyArrayList<T> getInstance(Class<T> elemClazz, Collection<? extends T> c) {
        return new MyArrayList<>(c);
    }

    @Deprecated
    public static <T> MyArrayList<T> asList(T... a) {
        List<T> ts = Arrays.asList(a);
        return new MyArrayList<>(ts);
    }

    @Deprecated
    public static <T> MyArrayList<T> getDefaultList(Class<T> elemClazz) {
        return new MyArrayList<>();
    }


}
