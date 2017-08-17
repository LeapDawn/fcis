package g11.service;

import g11.dto.RequestList;
import g11.dto.ResultModel;
import g11.dto.pageModel.Section;
import g11.model.AdvancedCollective;
import g11.model.StatisticsResult;

import java.util.List;
import java.util.Map;

/**
 * Created by cody on 2017/8/7.
 */
public interface AdvancedCollectiveService extends ImportAndExport, Download{

    /**
     * 申报先进集体信息
     * (注意:当届,时间)
     * @param ac 先进集体信息
     */
    int save(AdvancedCollective ac);


    /**
     * 修改先进集体信息
     * @param ac 先进集体信息
     */
    void update(AdvancedCollective ac);

    /**
     * 删除先进集体信息
     * @param ids 待删除集体信息编号,多条记录以`,`隔开
     */
    void delete(Section section);

    /**
     * 根据条件分页查询先进集体信息
     * @param rl
     * @return
     */
    ResultModel<AdvancedCollective> list(RequestList<AdvancedCollective> rl);

    /**
     * 转为历届
     * @param ids 待认定集体信息编号,多条记录以`,`隔开
     * @return
     */
    void overdue(Section section);

    /**
     * 统计
     * @param section 统计请求参数
     * @return 统计结果
     */
    List<Map<String,?>> statics(Section section);

    StatisticsResult getLastStaticsResult();
}
