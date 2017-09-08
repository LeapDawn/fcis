package g11.service;

import g11.dto.RequestList;
import g11.dto.ResultModel;

import g11.dto.pageModel.PAdvancedPerson;

import g11.dto.pageModel.Section;
import g11.model.AdvancedPerson;
import g11.model.StatisticsResult;


import java.util.List;
import java.util.Map;

/**
 * Created by cody on 2017/8/7.
 */
public interface AdvancedPersonService extends ImportAndExport, Download {

    /**
     * 申报先进个人
     * @param pAdvancedPerson 先进个人信息
     */
    public abstract int save(PAdvancedPerson pAdvancedPerson);

    /**
     * 删除先进个人信息
     * @param ids 待删除个人信息编号,多条记录以`,`隔开
     */
    public abstract void delete(Section section);

    /**
     * 根据条件分页查询先进个人信息
     * @param rl
     * @return
     */
    public abstract ResultModel<PAdvancedPerson> list(RequestList<PAdvancedPerson> rl);

    /**
     * 修改
     * @param pap 先进个人信息
     * @return
     */
    public abstract void update(PAdvancedPerson pap);

    /**
     * 统计
     * @param ss 统计请求参数
     * @return 统计结果
     */
    public abstract List<Map<String, ?>> statics(Section ss);

    /**
     * 认定
     * @param acList 待认定个人信息集合(先进个人信息ID,认定结果通过/不通过)
     * @return
     */
    public abstract void recognise(List<AdvancedPerson> acList);

    /**
     * 当届变历届
     * @param ids 先进个人id,多条记录以`,`隔开
     * @return
     */
    public abstract void overdue(Section section);

    StatisticsResult getLastStaticsResult();
}
