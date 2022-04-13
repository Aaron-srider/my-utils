package fit.wenchao.utils.function;

import org.springframework.web.server.handler.ExceptionHandlingWebHandler;

/**
 *
 * @param <T> 函数的第一个参数类型
 */
@FunctionalInterface
public interface ExceptionPredicate<T> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(T t) throws Exception;
}