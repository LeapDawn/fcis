package g11.service;

import g11.commons.exception.FileException;
import g11.dto.pageModel.PFile;

import java.util.List;

public interface DataBackupService {

    /**
     * 获取备份文件列表
     * @return
     */
    List<PFile> list() throws FileException;

    /**
     * 删除文件
     * @param fileName 待删除文件
     * @return
     */
    boolean delete(String fileName) throws FileException;

    /**
     * 数据库备份(文件名不应该重复)
     * @param fileName 备份文件生成名称
     * @return
     */
    void backup(String fileName);

    /**
     * 数据库还原
     * @param fileName 文件名称
     */
    void restore(String fileName);
}
