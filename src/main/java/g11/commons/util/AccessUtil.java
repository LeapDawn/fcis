package g11.commons.util;

/**
 *  判断是否具备某种权限
 */
public class AccessUtil {

    private final static int SYSTEM_MANAGER = 4;
    private final static int SPECIAL_QUERY = 2;
    private final static int INPUT = 1;

    /**
     * 是否具备管理员权限
     * @param function
     * @return
     */
    public static boolean isSystemManager(int function) {
        return (function & SYSTEM_MANAGER) == SYSTEM_MANAGER;
    }

    /**
     * 是否具备特殊信息查询权限
     * @param function
     * @return
     */
    public static boolean isSpecialQuery(int function) {
        return (function & SPECIAL_QUERY) == SPECIAL_QUERY;
    }

    /**
     * 是否具备录入权限
     * @param function
     * @return
     */
    public static boolean isInput(int function) {
        return (function & INPUT) == INPUT;
    }
}
