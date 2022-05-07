package fit.wenchao.utils.random;

import java.util.Random;

public class RandomUtils {
    /**
     * 随机生成一个区间内的整数（闭区间）
     * @param start 开始起始，可以为负数
     * @param end 区间末尾，可以为负数
     * @return 返回闭区间中的一个随机整数。
     */
    public static int randomIntRange(int start, int end) {
        if(start > end) {
            throw new IllegalArgumentException("start must not be greater than end");
        }
        return (new Random().nextInt(end - start + 1) + start);
    }

}
