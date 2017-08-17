package g11.dao;

import g11.model.DataDir;

import java.util.List;

public interface DataDirDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(DataDir record);

    int insertSelective(DataDir record);

    DataDir selectByPrimaryKey(Integer id);

    List<DataDir> selectAll();

    int updateByPrimaryKeySelective(DataDir record);

    int updateByPrimaryKey(DataDir record);
}