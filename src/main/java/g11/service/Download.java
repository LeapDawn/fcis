package g11.service;

import g11.commons.exception.ExcelException;
import g11.dto.pageModel.Section;

import java.io.File;

/**
 * Created by cody on 2017/8/7.
 */
public interface Download {

    /**
     * 下载信息(根据id)
     * @param file
     * @return
     * @throws ExcelException
     */
    public File downloadData(Section section, File file) throws ExcelException;

}
