package fit.wenchao.utils.function;

/**
 * <p>
 * 接收<em>两个参数</em>的函数式接口，特点是，内置函数将<em>抛出所有异常</em>，目的是不用在lambda中写try-catch，
 * 这在嵌套lambda的情况下能让代码变得简洁些。
 * </p>
 *
 * <p>
 * 使用下面demo来说明具体用意：
 * <pre>
 * {@code
 * public class Test{
 *     public static void compute(ExceptionBiConsumer<T,U> function) throws Exception{
 *         doSth();
 *         function.accept();
 *         doSth();
 *     }
 *
 *     public static void main(String[] args) {
 *         try{
 *             A.compute(() -> {
 *                  //try {
 *                      //....
 *                      InputStream in = ClassLoader.getResource();
 *                      Function f = (item) -> {
 *                          Connection conn = ConnectionFactory.getConnction();
 *                      }
 *                      //....
 *                  //} catch(Exception e) {
 *                      e.printStacktrace();
 *                  //}
 *             });
 *         } catch(Exception e) {
 *             //在lambda统一处理异常外面处理
 *             if(e instanceof AException) {
 *                 //process
 *             } else if(e instanceof BException) {
 *                 //process
 *             } else if(...) {
 *                 //process
 *             }
 *         }
 *     }
 * } }
 *</pre>
 *
 *
 * @param <T> 函数的第一个参数类型
 * @param <U> 函数的第二个参数类型
 */
@FunctionalInterface
public interface ExceptionBiConsumer<T,U> {

    /**
     * 处理指定的两个参数，没有返回值
     * @param t 函数的第一个参数
     * @param u 函数的第二个参数
     * @throws Exception 函数抛出所有类型的异常
     */
    void accept(T t, U u) throws Exception;
}