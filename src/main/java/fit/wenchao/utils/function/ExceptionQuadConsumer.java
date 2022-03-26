package fit.wenchao.utils.function;

@FunctionalInterface
public interface ExceptionQuadConsumer<T, U, W, X> {
    /**
     * 处理指定的两个参数，没有返回值
     *
     * @param t 函数的第一个参数
     * @param u 函数的第二个参数
     * @param w 函数的第三个参数
     * @param x 函数的第四个参数
     * @throws Exception 函数抛出所有类型的异常
     */
    void accept(T t, U u, W w, X x) throws Exception;
}