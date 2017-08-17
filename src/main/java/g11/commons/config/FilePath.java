package g11.commons.config;

import org.springframework.util.ClassUtils;

/**
 * Created by cody on 2017/8/9.
 */
public class FilePath {

    private final static String FILE_DIR_PATH;

    static {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        if (path.indexOf(".jar") != -1) {
            path = path.substring(0, path.indexOf(".jar"));
            path = path.substring(0, path.lastIndexOf("/"));
        }
        FILE_DIR_PATH = path;
    }

    /**
     * 获取导入目录
     * @return
     */
    public static String getBackupsDirPath(){
        return FILE_DIR_PATH + "/backups";
    }

    /**
     * 获取上传目录
     * @return
     */
    public static String getUploadDirPath(){
        return FILE_DIR_PATH + "/uploads";
    }
}
