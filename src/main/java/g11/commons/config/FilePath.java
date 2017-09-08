package g11.commons.config;

import org.springframework.util.ClassUtils;

import java.io.File;

/**
 * Created by cody on 2017/8/9.
 */
public class FilePath {

    public final static String FILE_DIR_PATH;

    static {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        if (path.indexOf(".jar") != -1) {
            path = path.substring(0, path.indexOf(".jar"));
            path = path.substring(0, path.lastIndexOf("/"));
        }
        if (path.startsWith("file:")){
            path = path.substring(5);
        }
        if (System.getProperty("os.name").toLowerCase().startsWith("win")
                && path.startsWith("/") && path.charAt(2) == ':') {
            path = path.substring(1);
        }

        FILE_DIR_PATH = path;
        File file = new File(path + "/uploads");
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(path + "/backups");
        if (!file.exists()) {
            file.mkdirs();
        }
        System.out.println(FILE_DIR_PATH);
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
