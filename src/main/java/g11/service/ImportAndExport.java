package g11.service;

import g11.commons.exception.ExcelException;
import g11.dto.UploadResultModel;
import g11.dto.pageModel.Section;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

/**
 * excel文件的导入导出下载
 */
public interface ImportAndExport{

    /**
     * 导入信息
     * @param input
     * @param user
     * @return
     * @throws ExcelException
     */
    public UploadResultModel importData(InputStream input, String user) throws ExcelException;

    /**
     * 导出信息(根据时间区间)
     * @param section
     * @param file
     * @return
     * @throws ExcelException
     */
    public File exportData(Section section, File file) throws ExcelException;
}
