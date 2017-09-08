package g11.dao;

import g11.dto.pageModel.Section;
import g11.model.AdvancedCollective;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdvancedCollectiveDAO {
    //全属性
    int insert(AdvancedCollective record);

    //有选择性地插入
    int insertSelective(AdvancedCollective record);

    //批量删除
    int delete(@Param("array") String[] ids, @Param("isCurrent") byte isCurrent);

    //批量查找，全属性
    List<AdvancedCollective> selectByPrimaryKeys(@Param("array") String[] ids, @Param("isCurrent") byte isCurrent);

    //按日期区间查找
    List<AdvancedCollective> selectByBegindateAndEnddate(Section section);

    //分页查询
    List<AdvancedCollective> list(@Param("ac") AdvancedCollective ac, @Param("start") int start, @Param("rows") int rows);

    //计算符合条件的记录数
    int count(AdvancedCollective ac);

    //选择性修改
    int updateByPrimaryKeySelective(AdvancedCollective record);

    //批量过期
    int overdue(String[] ids);

    //按荣誉称号统计指定时间区间里面的先进集体
    List<Map<String,?>> statics(Section section);
}