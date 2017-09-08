package g11.service.impl;

import g11.commons.exception.ExcelException;
import g11.commons.util.IDGenerator;
import g11.dao.AdvancedCollectiveDAO;
import g11.dto.RequestList;
import g11.dto.ResultModel;
import g11.dto.pageModel.PAdvancedPerson;
import g11.dto.pageModel.Section;
import g11.model.AdvancedCollective;
import g11.service.AdvancedCollectiveService;
import g11.service.AdvancedPersonService;
import g11.service.AllModelWorkerService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

/**
 * Created by user on 2017/8/9.
 */
@Service
public class AllModelWorkerServiceImpl implements AllModelWorkerService {
    @Setter
    @Autowired
    private AdvancedCollectiveService advancedCollectiveService;
    @Setter
    @Autowired
    private AdvancedPersonService advancedPersonService;
    @Setter
    @Autowired
    private AdvancedCollectiveDAO advancedCollectiveDAO;
    /**
     * 新增先进个人历届劳模
     *

     */
    @Override
    public void saveAdvancedPerson(PAdvancedPerson pAdvancedPerson) {
        pAdvancedPerson.setIsCurrent((byte)0);
        advancedPersonService.save(pAdvancedPerson);
    }

    /**
     * 新增先进集体历届劳模
     *
     * @param ac 先进集体信息
     */
    @Override
    public void saveAdvancedCollective(AdvancedCollective ac)  {
        ac.setId(IDGenerator.generatorUniqueLongId().toString());
        ac.setIsCurrent((byte)0);
        ac.setAdditionTime(new Date());
        advancedCollectiveDAO.insert(ac);
    }

    /**
     * 删除先进个人历届劳模
     *
     */
    @Override
    public void deleteAdvancedPerson(Section section) {
        advancedPersonService.delete(section);
    }

    /**
     * 删除先进集体历届劳模
     *
     * @param ids 待删除先进集体历届劳模编号,多条记录以`,`隔开
     */
    @Override
    public void deleteAdvancedCollective(String ids) {
        String[] id= ids.split(",");
        advancedCollectiveDAO.delete(id,(byte)0);
    }

    /**
     * 根据条件分页查询先进个人历届劳模
     *
     * @param rl
     * @return
     */
    @Override
    public ResultModel<PAdvancedPerson> listAdvancedPerson(RequestList<PAdvancedPerson> rl) {
        PAdvancedPerson advancedPerson=rl.getKey();
        advancedPerson.setIsCurrent((byte)0);
        rl.setKey(advancedPerson);
       return advancedPersonService.list(rl);
    }

    /**
     * 根据条件分页查询先进集体历届劳模
     *
     * @param rl
     * @return
     */
    @Override
    public ResultModel<AdvancedCollective> listAdvancedCollective(RequestList<AdvancedCollective> rl) {
       AdvancedCollective advancedCollective=rl.getKey();
       advancedCollective.setIsCurrent((byte)0);
       rl.setKey(advancedCollective);
        return advancedCollectiveService.list(rl);
    }

    /**
     * 修改先进个人历届劳模
     *

     * @return
     */
    @Override
    public void updateAdvancedPerson(PAdvancedPerson pAdvancedPerson) {
        pAdvancedPerson.setIsCurrent((byte)0);
        advancedPersonService.update(pAdvancedPerson);
    }

    /**
     * 修改先进集体历届劳模
     *
     * @param ac 先进集体信息
     */
    @Override
    public void updateAdvancedCollective(AdvancedCollective ac) {
        ac.setIsCurrent((byte)0);
        advancedCollectiveDAO.updateByPrimaryKeySelective(ac);
    }
@Override
    public File downloadData(Section section, File file) {
        File file1 = null;
        try {
            if(section.getType()==(byte)1) {
                section.setIsCurrent((byte)0);
                file1 = advancedCollectiveService.downloadData(section, file);
            }
            if(section.getType()==(byte)0){
                section.setIsCurrent((byte)0);
                file1 = advancedPersonService.downloadData(section,file);
            }
        } catch (ExcelException e) {
            System.out.println(e.getMessage());
        }
        return file1;
    }


}
