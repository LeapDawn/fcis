package g11.dao;

import g11.dto.pageModel.PAdvancedPerson;
import g11.dto.pageModel.Section;
import g11.model.AdvancedPerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdvancedPersonDAO {
    int deleteByPrimaryKey(String[] id);

    int insert(AdvancedPerson record);

    int insertSelective(AdvancedPerson record);

    List<PAdvancedPerson> selectByPrimaryKeys(@Param("array") String[] id, @Param("isCurrent") Byte isCurrent);
    List<PAdvancedPerson> selectByPrimaryKeysin(String id);

    List<String> selectCid(String[] id);

    List<PAdvancedPerson> selectByDATE(Section section);

    int count(@Param("pa") PAdvancedPerson pa);

    List<PAdvancedPerson> dividepageselect(@Param("pa") PAdvancedPerson pa, @Param("skip") int skip, @Param("rows") int rows);

    int upstatus(@Param("id") String id, @Param("status") int status, @Param("statusinformation") String statusinformation);

    int overdue(String[] ids);
    List<Map<String,?>> staticsByTitle(Section section);
    List<Map<String,?>> staticsByNation(Section section);
    List<Map<String,?>> staticsByCity(Section section);
    int updateByPrimaryKeySelective(AdvancedPerson record);

    int updateByPrimaryKey(AdvancedPerson record);


}