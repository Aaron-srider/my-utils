package fit.wenchao.utils.timer;
import fit.wenchao.utils.function.VoidFunction;

/**
 * 方便地测量一段代码的执行时间，使用示例如下：
 *<pre>
 *     {@code
 * public static void main(String[] args) {
 *     Timer timer = new Timer();
 *     long elapse = timer.getMilliElapse(
 *             () -> {
 *                 for (int i = 0; i < 10; i++) {
 *                     Thread.sleep(10000);
 *                 }
 *             }
 *     );
 *     System.out.println(elapse + "ms");
 * }}</pre>
 */
public class Timer {

    /**
     * 获取一段代码的执行时间
     * @param target 待测量的过程代码
     * @return 测得目标代码的运行时间（毫秒级）
     */
    public <T> long getMilliElapse(VoidFunction target) throws Exception {
        long start = System.currentTimeMillis();
        target.apply();
        long end = System.currentTimeMillis();
        return end - start;
    }

    /**
     * 判断时间是否符合预期
     *
     * @param elapse 待判断的时间
     * @return 如果时间符合预期，返回true
     */
    public static boolean ifTimeMeetsExpectation(long elapse, long expected) {
        return elapse <= expected;
    }
}
