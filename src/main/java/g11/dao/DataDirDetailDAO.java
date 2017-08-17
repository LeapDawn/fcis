package g11.dao;

import g11.model.DataDirDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataDirDetailDAO {
    int delete(Integer[] ids);

    int deleteByPrimaryKey(Integer id);

    int insert(DataDirDetail record);

    int insertSelective(DataDirDetail record);

    DataDirDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DataDirDetail record);

    int updateByPrimaryKey(DataDirDetail record);

    List<DataDirDetail> selectByDataDir(@Param("data_dir") Integer DataDirId);
}