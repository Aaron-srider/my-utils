package fit.wenchao.utils.timer;

import org.junit.Test;


public class TimerTest {

    @Test
    public void get_milli_elapse() throws Exception {
        Timer timer = new Timer();

        long elapse = timer.getMilliElapse(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(10);
                    }
                }
        );

        System.out.println(elapse + "ms");
    }

    @Test
    public void testGet_milli_elapse() {
    }
}