package g11.commons.util;

/**
 * 生成各个字段的ID
 */
public class IDGenerator {

    private static final int LONG_ID_LENGTH = 14;

    public static Long generatorLongId() {
        Long result = 0L;
        int tmp = (int)(Math.random() * 10);
        tmp = tmp > 0 ? tmp : 1;
        result += tmp;
        for (int i = 1; i < IDGenerator.LONG_ID_LENGTH; i++) {
            result = result * 10 + (int)(Math.random() * 10);
        }
        return result;
    }

    public static Long generatorUniqueLongId() {
        Long result = System.currentTimeMillis() % 10000000000L;
        for (int i = 0, length = ("" + result).length(); i < IDGenerator.LONG_ID_LENGTH - length; i++) {
            result = result * 10 + (int)(Math.random() * 10);
        }
        return result;
    }
}
