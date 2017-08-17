package g11.commons.util;

import g11.commons.exception.FileException;
import g11.dto.pageModel.PFile;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文件操作工具类
 */
public class FileUtil {

    /**
     * 获取文件路径下的文件名称
     * @param path 待扫描路径
     * @return 文件名称集合
     * @throws FileException
     */
    public static List<PFile> getFileNames (String path) throws FileException {
        List<PFile> list = new ArrayList<>();
        File Dir = new File(path);
        isDirectory(Dir);
        File[] files = Dir.listFiles();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    list.add(new PFile(file.getName(), format.format(new Date(file.lastModified()))));
                }
            }
        }
        return list;
    }

    /**
     * @param path 待删除文件路径, 文件不存在/不是目录则抛出FileException
     * @return 删除结果
     * @throws FileException
     */
    public static boolean deleteFile(String path) throws FileException {
        File file = new File(path);
        isFile(file);
        return file.delete();
    }

    /**
     * 验证路径是否指向一个存在的文件,不存在或者不是文件则抛出FileException
     * @param path 文件路径
     * @throws FileException
     */
    public static boolean isExistsFile(String path) {
        File file = new File(path);
        System.out.println(file.getAbsoluteFile());
        System.out.println(file.getPath());
        try {
            isFile(file);
            return true;
        } catch (FileException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 验证路径是否指向一个存在的目录,不存在或者不是目录则抛出FileException
     * @param path 目录路径
     * @throws FileException
     */
    public static boolean isExistsDirectory(String path){
        File file = new File(path);
        try {
            isDirectory(file);
            return true;
        } catch (FileException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建目录
     * @param path 目录
     * @return
     */
    public static boolean createDir(String path) {
        File file = new File(path);
        return file.mkdirs();
    }

    /** 验证路径所指文件/目录是否存在 */
    private static void isExists(File file) throws FileException {
        if (!file.exists()) {
            throw new FileException("路径错误");
        }
    }

    /** 验证是否为文件 */
    private static void isFile(File file) throws FileException {
        isExists(file);
        if (!file.isFile()) {
            throw new FileException("该路径不是一个文件路径");
        }
    }

    /** 验证是否为目录 */
    private static void isDirectory(File file) throws FileException {
        isExists(file);
        if (!file.isDirectory()) {
            throw new FileException("该路径不是一个目录路径");
        }
    }
}
