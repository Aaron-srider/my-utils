package fit.wenchao.utils.random;

import org.apache.commons.lang3.RandomStringUtils;
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

    public static String randomAllCharFor(int len) {
        String random = RandomStringUtils.random(len);
        return random;
    }

    public static String randomFromCharset(int len , String charSet) {
        return RandomStringUtils.random(len, charSet);
    }

    public static String randomStringFromDigitalFor(int len) {
        return RandomStringUtils.randomNumeric(len);
    }

    public static String randomStringFromDigitalFor(int minLen ,int maxLen) {
        return RandomStringUtils.randomNumeric(minLen, maxLen + 1);
    }

    public static String randomStringFromAlphabetFor(int len) {
        return RandomStringUtils.randomAlphabetic(len);
    }

    public static String randomStringFromAlphabetFor(int minLen, int maxLen) {
        return RandomStringUtils.randomAlphabetic(minLen, maxLen + 1);
    }

    public static String randomStringFromAlphaDigital(int len) {
        return RandomStringUtils.randomAlphanumeric(len);
    }
    public static String randomStringFromAlphaDigital(int minLen, int maxLen) {
        return RandomStringUtils.randomAlphanumeric(minLen, maxLen + 1);
    }
    public static String randomStringFromVisibleAscii(int len) {
        return RandomStringUtils.randomAscii(len);
    }

    public static String randomStringFromVisibleAscii(int minLen, int maxLen) {
        return RandomStringUtils.randomAscii(minLen, maxLen + 1);
    }

}
