package g11.service;

import g11.model.DataDir;
import g11.model.DataDirDetail;

import java.util.List;

/**
 * Created by cody on 2017/8/7.
 */
public interface DataDirService {

    /**
     * 返回所有数据字典项
     * (不用分页)
     * @return
     */
    List<DataDir> listDataDir();

    /**
     * 保存新的数据字典明细
     * @param detail 待保存明细信息
     */
    void save(DataDirDetail detail);

    /**
     * 更新数据字典明细信息
     * @param detail 待更新明细信息
     */
    void update(DataDirDetail detail);

    /**
     * 删除数据字典明细信息
     * (若明细信息被引用,不允许被删除)
     * @param ids 待删除明细信息ID,多个记录以`,`隔开
     * @return 成功删除的明细信息数
     */
    int delete(String ids);

    /**
     * 查询某个数据字典项下的数据字典明细信息
     * @param dataDir 数据字典项
     * @return 明细信息集合
     */
    List<DataDirDetail> listDataDirDetail(Integer dataDir);
}
