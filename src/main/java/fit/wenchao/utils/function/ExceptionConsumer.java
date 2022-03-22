package fit.wenchao.utils.function;
@FunctionalInterface
public interface ExceptionConsumer<T> {
    void accept(T t) throws Exception;
}