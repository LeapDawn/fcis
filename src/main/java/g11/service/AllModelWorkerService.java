package g11.service;

import g11.dto.RequestList;
import g11.dto.ResultModel;
import g11.model.AdvancedCollective;
import g11.model.AdvancedPerson;

/**
 * Created by cody on 2017/8/7.
 */
public interface AllModelWorkerService extends Download{
    /**
     * 新增先进个人历届劳模
     * @param ap 先进个人信息
     */
    void saveAdvancedPerson(AdvancedPerson ap);

    /**
     * 新增先进集体历届劳模
     * @param ac 先进集体信息
     */
    void saveAdvancedCollective(AdvancedCollective ac);

    /**
     * 删除先进个人历届劳模
     * @param ids 待删除先进个人历届劳模编号,多条记录以`,`隔开
     */
    void deleteAdvancedPerson(String ids);

    /**
     * 删除先进集体历届劳模
     * @param ids 待删除先进集体历届劳模编号,多条记录以`,`隔开
     */
    void deleteAdvancedCollective(String ids);

    /**
     * 根据条件分页查询先进个人历届劳模
     * @param rl
     * @return
     */
    ResultModel<AdvancedPerson> listAdvancedPerson(RequestList<AdvancedPerson> rl);

    /**
     * 根据条件分页查询先进集体历届劳模
     * @param rl
     * @return
     */
    ResultModel<AdvancedCollective> listAdvancedCollective(RequestList<AdvancedCollective> rl);

    /**
     * 修改先进个人历届劳模
     * @param ap 先进个人信息
     * @return
     */
    void updateAdvancedPerson(AdvancedPerson ap);

    /**
     * 修改先进集体历届劳模
     * @param ac 先进集体信息
     */
    void updateAdvancedCollective(AdvancedCollective ac);

}
