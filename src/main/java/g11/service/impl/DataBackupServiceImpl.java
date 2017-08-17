package g11.service.impl;

import g11.commons.config.FilePath;
import g11.commons.exception.DataViolationException;
import g11.commons.exception.FileException;
import g11.commons.util.BackupCommand;
import g11.commons.util.FileUtil;
import g11.dto.pageModel.PFile;
import g11.service.DataBackupService;
import lombok.Setter;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 数据备份业务逻辑
 */
@Service
public class DataBackupServiceImpl implements DataBackupService {

    @Setter
    @Autowired
    private DataSource dataSource;

    @Override
    public List<PFile> list() throws FileException {
        // 若备份目录不存在,生成它
        if (!FileUtil.isExistsDirectory(FilePath.getBackupsDirPath())) {
            FileUtil.createDir(FilePath.getBackupsDirPath());
            return new ArrayList<>();
        }
        try {
            List<PFile> list = FileUtil.getFileNames(FilePath.getBackupsDirPath());
            Collections.sort(list);
            return list;
        } catch (FileException e) {
            throw new FileException(401, "获取文件列表失败");
        }
    }

    @Override
    public boolean delete(String fileName) throws FileException {
        try {
            return FileUtil.deleteFile(FilePath.getBackupsDirPath() + "/" + fileName);
        } catch (FileException e) {
            throw new FileException(402, "删除失败,不存在该文件");
        }
    }

    @Override
    public void backup(String fileName) {
        // 若备份目录不存在,生成它
        if (!FileUtil.isExistsDirectory(FilePath.getBackupsDirPath())) {
            FileUtil.createDir(FilePath.getBackupsDirPath());
        }
        String filePath = FilePath.getBackupsDirPath() + "/" + fileName;
        if (FileUtil.isExistsFile(filePath) ){
            throw new DataViolationException(404, "该备份文件已经存在");
        }
        // 获取备份命令
        String backupCommand = BackupCommand.getBackupCommand(filePath);
        System.out.println(backupCommand);
        // 执行备份操作
        try {
            Runtime.getRuntime().exec(backupCommand);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataViolationException(405, "数据备份失败");
        }
    }

    @Transactional
    @Override
    public void restore(String fileName) {
        String filePath = FilePath.getBackupsDirPath() + "/" + fileName;
        if (!FileUtil.isExistsFile(filePath) ){
            throw new DataViolationException(406, "该备份文件不存在");
        }
        try {
            ScriptRunner runner = new ScriptRunner(dataSource.getConnection());
            runner.runScript(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
        } catch (Exception e) {
            throw new DataViolationException(407, "还原备份文件失败");
        }
    }
}
