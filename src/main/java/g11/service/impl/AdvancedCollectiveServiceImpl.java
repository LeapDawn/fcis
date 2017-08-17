package g11.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import g11.commons.excelModel.AdvancedCollectiveExcelModel;
import g11.commons.exception.DataViolationException;
import g11.commons.exception.ExcelException;
import g11.commons.util.ExcelUtil;
import g11.commons.util.IDGenerator;
import g11.dao.AdvancedCollectiveDAO;
import g11.dao.StatisticsResultDAO;
import g11.dto.RequestList;
import g11.dto.ResultModel;
import g11.dto.pageModel.Section;
import g11.dto.UploadResultModel;
import g11.model.AdvancedCollective;
import g11.model.StatisticsResult;
import g11.service.AdvancedCollectiveService;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/8/9.
 */
@Service
public class AdvancedCollectiveServiceImpl implements AdvancedCollectiveService {

    @Autowired
    private AdvancedCollectiveDAO acDao;

    @Autowired
    private StatisticsResultDAO srDao;

    //服务端先创建一个文件@Prama file（来自控制层），写进数据，传给客户端，然后把文件删掉（由控制层来）
    //使用section中的ids
    @Override
    public File downloadData(Section section, File file) throws ExcelException {
        ExcelUtil excelUtil = new ExcelUtil();
        String[] headArray = AdvancedCollectiveExcelModel.getOutputAdvancedCollectiveExcelModel();
        List<Map<String, Object>> bodyList = new ArrayList<Map<String, Object>> ();
        List<AdvancedCollective> acList = acDao.selectByPrimaryKeys(section.getIds().split(","),section.getIsCurrent());
        Map<String, Object> map = null;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        for (AdvancedCollective ac : acList) {
            map = new HashMap<String,Object>();
            map.put(AdvancedCollectiveExcelModel.ID,ac.getId());
            map.put(AdvancedCollectiveExcelModel.NAME,ac.getName());
            map.put(AdvancedCollectiveExcelModel.HONORARY_TITLE,ac.getHonoraryTitle());
            map.put(AdvancedCollectiveExcelModel.OWNED_CITY_INDUSTRY,ac.getOwnedCityIndustry());
            map.put(AdvancedCollectiveExcelModel.PRINCIPAL_NAME,ac.getPrincipalName());
            map.put(AdvancedCollectiveExcelModel.CONTACT_DETAIL,ac.getContactDetail());
            map.put(AdvancedCollectiveExcelModel.OUTSTANDING_DEED,ac.getOutstandingDeed());
            map.put(AdvancedCollectiveExcelModel.IS_CURRENT,ac.getIsCurrent() == 1 ?"是":"否");
            map.put(AdvancedCollectiveExcelModel.ADDITION_TIME,sdf.format(ac.getAdditionTime()));
            bodyList.add(map);
        }
        excelUtil.setHeadArray(headArray);
        excelUtil.setBodyList(bodyList);
        excelUtil.writeExcel(file,"先进集体信息查询结果");
        return file;
    }

    @Override
    public UploadResultModel importData(InputStream input, String user) throws ExcelException {
        ExcelUtil excelUtil = new ExcelUtil();
        List<Map<String, Object>> bodyList = excelUtil.readExcel(input,AdvancedCollectiveExcelModel.getInputAdvancedCollectiveExcelModel());
        AdvancedCollective ac = null;

        UploadResultModel uploadResultModel = new UploadResultModel();
        int total = 0; // 导入总记录数
        int error = 0; // 失败记录数
        int success = 0; // 成功记录数
        List<AdvancedCollective> data = new ArrayList<AdvancedCollective>(); // 导入失败数据
        for (Map<String, Object> map: bodyList){
            total ++;
           ac = new AdvancedCollective();
            ac.setName((String) map.get(AdvancedCollectiveExcelModel.NAME));
            ac.setHonoraryTitle((String) map.get(AdvancedCollectiveExcelModel.HONORARY_TITLE));
            ac.setOwnedCityIndustry((String) map.get(AdvancedCollectiveExcelModel.OWNED_CITY_INDUSTRY));
            ac.setPrincipalName((String) map.get(AdvancedCollectiveExcelModel.PRINCIPAL_NAME));
            ac.setContactDetail((String) map.get(AdvancedCollectiveExcelModel.CONTACT_DETAIL));
            ac.setOutstandingDeed((String) map.get(AdvancedCollectiveExcelModel.OUTSTANDING_DEED));
            if(save(ac) == 0){
                error ++;
                data.add(ac);
            }
            else {
                success ++;
            }
        }
        uploadResultModel.setTotal(total);
        uploadResultModel.setError(error);
        uploadResultModel.setSuccess(success);
        uploadResultModel.setData(data);
        return uploadResultModel;
    }

    //使用section中的beginDate和endDate
    @Override
    public File exportData(Section section ,File file) throws ExcelException {
        ExcelUtil excelUtil = new ExcelUtil();
        String[] headArray = AdvancedCollectiveExcelModel.getOutputAdvancedCollectiveExcelModel();
        List<Map<String, Object>> bodyList = new ArrayList<Map<String, Object>> ();
        List<AdvancedCollective> acList = acDao.selectByBegindateAndEnddate(section);
        Map<String, Object> map = null;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        for (AdvancedCollective ac : acList) {
            map = new HashMap<String,Object>();
            map.put(AdvancedCollectiveExcelModel.ID,ac.getId());
            map.put(AdvancedCollectiveExcelModel.NAME,ac.getName());
            map.put(AdvancedCollectiveExcelModel.HONORARY_TITLE,ac.getHonoraryTitle());
            map.put(AdvancedCollectiveExcelModel.OWNED_CITY_INDUSTRY,ac.getOwnedCityIndustry());
            map.put(AdvancedCollectiveExcelModel.PRINCIPAL_NAME,ac.getPrincipalName());
            map.put(AdvancedCollectiveExcelModel.CONTACT_DETAIL,ac.getContactDetail());
            map.put(AdvancedCollectiveExcelModel.OUTSTANDING_DEED,ac.getOutstandingDeed());
            map.put(AdvancedCollectiveExcelModel.IS_CURRENT,ac.getIsCurrent() == 1 ?"是":"否");
            map.put(AdvancedCollectiveExcelModel.ADDITION_TIME,sdf.format(ac.getAdditionTime()));
            bodyList.add(map);
        }
        excelUtil.setHeadArray(headArray);
        excelUtil.setBodyList(bodyList);
        excelUtil.writeExcel(file,"先进集体信息导出结果");
        return file;
    }

    @Override
    public int save(AdvancedCollective ac) {
         ac.setId(IDGenerator.generatorUniqueLongId().toString());
         ac.setIsCurrent((byte)1);
         ac.setAdditionTime(new Date(System.currentTimeMillis()));
         return acDao.insert(ac);
    }

    @Override
    public void update(AdvancedCollective ac) {
        ac.setIsCurrent((byte)1);
        acDao.updateByPrimaryKeySelective(ac);
    }

    @Override
    public void delete(Section section) {
        acDao.delete(section.getIds().split(","),(byte)1);
    }

    @Override
    public ResultModel<AdvancedCollective> list(RequestList<AdvancedCollective> rl) {
        AdvancedCollective ac = rl.getKey();
        ResultModel<AdvancedCollective> resultModel = new ResultModel(
                acDao.count(ac),
                rl.getRows(),
                rl.getPage(),
                acDao.list(ac,(rl.getPage()-1) * rl.getRows() ,rl.getRows()));
        return resultModel;
    }

    //批量过期
    @Override
    public void overdue(Section section) {
        acDao.overdue(section.getIds().split(","));
    }


    //统计
    @Override
    public List<Map<String,?>> statics(Section section) {
        List<Map<String,?>> list = acDao.statics(section);
        try {
            StatisticsResult collectiveStatisticsResult = StatisticsResult.getCollectiveStatisticsResult(list);
            srDao.insert(collectiveStatisticsResult);
        } catch (JsonProcessingException e) {
            throw new DataViolationException(104,"List转String异常");
        }
        return list;
    }

    public StatisticsResult getLastStaticsResult(){
        return srDao.selectLast();
    }
}
