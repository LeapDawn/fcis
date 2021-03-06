package g11.dao;
import g11.model.CertificationMaterials;

import java.util.List;

public interface CertificationMaterialsDAO {
    int deleteByPrimaryKey(String id);

    int insert(CertificationMaterials record);

    int insertSelective(CertificationMaterials record);

    List<CertificationMaterials> selectByPrimaryKey(String[] id);
    CertificationMaterials  selectByPrimaryKeysin(String id);

    int updateByPrimaryKeySelective(CertificationMaterials record);

    int updateByPrimaryKey(CertificationMaterials record);
}