package g11.dao;

import g11.model.HistoryTitle;

import java.util.List;

public interface HistoryTitleDAO {
    //根据主码批量删除
    int deleteByPrimaryKey(String[] id);
    //根据先进个人id批量删除
    int deleteByAPID(String[] apid);
    //插入一条数据
    int insert(HistoryTitle record);
    //插入数据（部分属性有）
    int insertSelective(HistoryTitle record);
    //根据主码批量查询
    List<HistoryTitle> selectByPrimaryKey(String[] id);
    //根据先进个人id批量删除
    List<HistoryTitle> findByApId(String apid);
    //根据主码更新（部分属性）
    int updateByPrimaryKeySelective(HistoryTitle record);
    //根据主码更新
    int updateByPrimaryKey(HistoryTitle record);
}