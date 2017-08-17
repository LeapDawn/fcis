package g11.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import g11.commons.excelModel.AdvancedCollectiveExcelModel;
import g11.commons.excelModel.AdvancedPersonExcelModel;
import g11.commons.exception.DataViolationException;
import g11.commons.exception.ExcelException;
import g11.commons.util.ExcelUtil;
import g11.commons.util.FileUtil;
import g11.commons.util.IDGenerator;
import g11.dao.AdvancedPersonDAO;
import g11.dao.CertificationMaterialsDAO;
import g11.dao.HistoryTitleDAO;
import g11.dao.StatisticsResultDAO;
import g11.dto.RequestList;
import g11.dto.ResultModel;
import g11.dto.StatisticsSection;
import g11.dto.UploadResultModel;
import g11.dto.pageModel.PAdvancedPerson;
import g11.dto.pageModel.Pdsearch;
import g11.dto.pageModel.Section;
import g11.model.*;
import g11.service.AdvancedPersonService;
import jxl.write.WriteException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 接口的实现类
 */
@Service
public class AdvancedPersonServiceImpl implements AdvancedPersonService {

    @Autowired
    @Setter
    private AdvancedPersonDAO advancedPersonDAO;
    @Autowired
    @Setter
    private CertificationMaterialsDAO certificationMaterialsDAO;
    @Autowired
    @Setter
    private HistoryTitleDAO historyTitleDAO;
    @Autowired
    @Setter
    private StatisticsResultDAO srDao;
    @Override
    public File downloadData(Section section, File file) throws ExcelException, IOException, WriteException {
        FileUtil fileUtil=new FileUtil();

        ExcelUtil excelUtil = new ExcelUtil();
        String[] headArray = AdvancedPersonExcelModel.getoutputAdvancedPersonExcelModel();
        List<Map<String, Object>> bodyList = new ArrayList<Map<String, Object>> ();
        List<PAdvancedPerson> lap= advancedPersonDAO.selectByDATE(section);
        Map<String, Object> map = null;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        for(PAdvancedPerson pap:lap){

            map = new HashMap<String,Object>();
            map.put(AdvancedPersonExcelModel.ID,pap.getId());
            map.put(AdvancedPersonExcelModel.honorary_title,pap.getHonoraryTitle());
            switch (pap.getTreatment()){
                case 1:map.put(AdvancedPersonExcelModel.treatment,"享受全国劳动模范待遇");break;
                case 2:map.put(AdvancedPersonExcelModel.treatment,"享受省、部级劳动模范待遇");break;
                case 3:map.put(AdvancedPersonExcelModel.treatment,"不能享受全国劳动模范待遇");break;
                case 4:map.put(AdvancedPersonExcelModel.treatment,"不能享受省、部级劳动模范待遇");break;

            }
            map.put(AdvancedPersonExcelModel.name,pap.getName());
            map.put(AdvancedPersonExcelModel.gender,pap.getGender()==1?"男":"女");
            map.put(AdvancedPersonExcelModel.nation,pap.getNation());
            map.put(AdvancedPersonExcelModel.birthday,pap.getBirthday());
            map.put(AdvancedPersonExcelModel.culturallevel,pap.getCulturalLevel());
            map.put(AdvancedPersonExcelModel.politicalstatus,pap.getPoliticalStatus());
            map.put(AdvancedPersonExcelModel.owendcityindustry,pap.getOwnedCityIndustry());
            map.put(AdvancedPersonExcelModel.organization,pap.getOrganization());
            map.put(AdvancedPersonExcelModel.duty,pap.getDuty());
            map.put(AdvancedPersonExcelModel.identitycard,pap.getIdentityCard());
            map.put(AdvancedPersonExcelModel.tel,pap.getTel());
            switch (pap.getStatus()){
                case 1:map.put(AdvancedPersonExcelModel.Status,"未认定");break;
                case 2:map.put(AdvancedPersonExcelModel.Status,"新认定");break;
                case 3:map.put(AdvancedPersonExcelModel.Status,"死亡");break;
                case 4:map.put(AdvancedPersonExcelModel.Status,"调动");break;
                case 5:map.put(AdvancedPersonExcelModel.Status,"取消");break;
            }
            map.put(AdvancedPersonExcelModel.statusimformation,pap.getStatusInformation());

            map.put(AdvancedPersonExcelModel.iscurrent,pap.getIsCurrent()==1?"是":"否");
            map.put(AdvancedPersonExcelModel.additiontime,sdf.format(pap.getAdditionTime()));
            map.put(AdvancedPersonExcelModel.physicalcondition,pap.getPhysicalCondition());
            map.put(AdvancedPersonExcelModel.family_difficulties_and_employment,pap.getFamilyDifficultiesAndEmployment());
            map.put(AdvancedPersonExcelModel.outstanding_deed,pap.getOutstandingDeed());
            map.put(AdvancedPersonExcelModel.cmid,pap.getCertificationMaterials().getId());
            map.put(AdvancedPersonExcelModel.grantunit,pap.getCertificationMaterials().getGrantUnit());
            map.put(AdvancedPersonExcelModel.granttime,sdf.format(pap.getCertificationMaterials().getGrantTime()));
            map.put(AdvancedPersonExcelModel.file_name_number,pap.getCertificationMaterials().getFileNameNumber());
            map.put(AdvancedPersonExcelModel.issuing_unit,pap.getCertificationMaterials().getIssuingUnit());
            map.put(AdvancedPersonExcelModel.profile_name,pap.getCertificationMaterials().getProfileName());
            map.put(AdvancedPersonExcelModel.ctreatment,pap.getCertificationMaterials().getTreatment());
            map.put(AdvancedPersonExcelModel.deed_briefing,pap.getCertificationMaterials().getDeedBriefing());
            map.put(AdvancedPersonExcelModel.certification_base,pap.getCertificationMaterials().getCertificationBase());
            map.put(AdvancedPersonExcelModel.opinion,pap.getCertificationMaterials().getOpinion());
        }
        excelUtil.setHeadArray(headArray);
        excelUtil.setBodyList(bodyList);
        excelUtil.writeExcel(file,"先进个人查询结果结果");
        return file;

    }

    @Override
    public UploadResultModel importData(InputStream input, String user) throws ExcelException {
        ExcelUtil excelUtil = new ExcelUtil();
        List<Map<String, Object>> bodyList = excelUtil.readExcel(input,AdvancedPersonExcelModel.getinputAdvancedPersonExcelModel());
        PAdvancedPerson pa=null;
        UploadResultModel uploadResultModel = new UploadResultModel();
        int total = 0; // 导入总记录数
        int error = 0; // 失败记录数
        int success = 0; // 成功记录数
        List<PAdvancedPerson> data = new ArrayList<PAdvancedPerson>(); // 导入失败数据
        for (Map<String, Object> map: bodyList){
            total ++;
            pa=new PAdvancedPerson();
            pa.setId((String)map.get(AdvancedPersonExcelModel.ID));
            pa.setHonoraryTitle((String)map.get(AdvancedPersonExcelModel.honorary_title));
            String h=(String)map.get(AdvancedPersonExcelModel.honorary_title);
            if(h=="享受全国劳动模范待遇")
            pa.setTreatment((Integer)1);
            else if(h=="享受省、部级劳动模范待遇")
                pa.setTreatment((Integer)2);
                else if(h=="不能享受全国劳动模范待遇")
                    pa.setTreatment((Integer)3);
                    else  if(h=="不能享受省、部级劳动模范待遇");
                    pa.setTreatment(4);
            pa.setName((String) map.get(AdvancedPersonExcelModel.name));
            if((String)map.get(AdvancedPersonExcelModel.gender)=="男")
                pa.setGender((byte)1);
            else
                pa.setGender((byte)0);
            pa.setNation((String)map.get(AdvancedPersonExcelModel.nation));
            pa.setBirthday((Date)map.get(AdvancedPersonExcelModel.birthday));
            pa.setCulturalLevel((String)map.get(AdvancedPersonExcelModel.culturallevel));
            pa.setPoliticalStatus((String)map.get(AdvancedPersonExcelModel.politicalstatus));
            pa.setOwnedCityIndustry((String)map.get(AdvancedPersonExcelModel.owendcityindustry));
            pa.setOrganization((String)map.get(AdvancedPersonExcelModel.organization));
            pa.setDuty((String)map.get(AdvancedPersonExcelModel.duty));
            pa.setIdentityCard((String)map.get(AdvancedPersonExcelModel.identitycard));
            pa.setTel((String)map.get(AdvancedPersonExcelModel.tel));
            if((String)map.get((AdvancedPersonExcelModel.Status))=="未认定")
                pa.setStatus(1);
            else if((String)map.get((AdvancedPersonExcelModel.Status))=="新认定")
                pa.setStatus(2);
            else if((String)map.get((AdvancedPersonExcelModel.Status))=="死亡")
                pa.setStatus(3);
            else if((String)map.get((AdvancedPersonExcelModel.Status))=="调动")
                pa.setStatus(4);
            else if((String)map.get((AdvancedPersonExcelModel.Status))=="取消")
                pa.setStatus(5);
            pa.setStatusInformation((String)map.get(AdvancedPersonExcelModel.statusimformation));
            pa.setPhysicalCondition((String)map.get(AdvancedPersonExcelModel.physicalcondition));
            pa.setFamilyDifficultiesAndEmployment((String)map.get(AdvancedPersonExcelModel.family_difficulties_and_employment));
            pa.setOutstandingDeed((String)map.get(AdvancedPersonExcelModel.outstanding_deed));
            CertificationMaterials c=new CertificationMaterials();
            c.setId((String)map.get(AdvancedPersonExcelModel.cmid));
            c.setGrantUnit((String)map.get(AdvancedPersonExcelModel.grantunit));
            c.setGrantTime((Date)map.get(AdvancedPersonExcelModel.granttime));
            c.setFileNameNumber((String)map.get(AdvancedPersonExcelModel.file_name_number));
            c.setIssuingUnit((String)map.get(AdvancedPersonExcelModel.issuing_unit));
            c.setProfileName((String)map.get(AdvancedPersonExcelModel.profile_name));
            c.setTreatment((String)map.get(AdvancedPersonExcelModel.ctreatment));
            c.setDeedBriefing((String)map.get(AdvancedPersonExcelModel.deed_briefing));
            c.setCertificationBase((String)map.get(AdvancedPersonExcelModel.certification_base));
            c.setOpinion((String)map.get(AdvancedPersonExcelModel.opinion));
            pa.setCertificationMaterials(c);
            if(this.save(pa) == 0){
                error ++;
                data.add(pa)
                ;
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

    @Override
    public File exportData(Section section , File file) throws ExcelException, IOException, WriteException {
        ExcelUtil excelUtil = new ExcelUtil();
        String[] headArray = AdvancedPersonExcelModel.getoutputAdvancedPersonExcelModel();
        List<Map<String, Object>> bodyList = new ArrayList<Map<String, Object>> ();
        List<PAdvancedPerson> alist=advancedPersonDAO.selectByDATE(section);
        Map<String, Object> map = null;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        for(PAdvancedPerson pap:alist){

            map = new HashMap<String,Object>();
            map.put(AdvancedPersonExcelModel.ID,pap.getId());
            map.put(AdvancedPersonExcelModel.honorary_title,pap.getHonoraryTitle());
            switch (pap.getTreatment()){
                case 1:map.put(AdvancedPersonExcelModel.treatment,"享受全国劳动模范待遇");break;
                case 2:map.put(AdvancedPersonExcelModel.treatment,"享受省、部级劳动模范待遇");break;
                case 3:map.put(AdvancedPersonExcelModel.treatment,"不能享受全国劳动模范待遇");break;
                case 4:map.put(AdvancedPersonExcelModel.treatment,"不能享受省、部级劳动模范待遇");break;

            }
            map.put(AdvancedPersonExcelModel.treatment,pap.getTreatment());
            map.put(AdvancedPersonExcelModel.name,pap.getName());
            map.put(AdvancedPersonExcelModel.gender,pap.getGender()==1?"男":"女");
            map.put(AdvancedPersonExcelModel.nation,pap.getNation());
            map.put(AdvancedPersonExcelModel.birthday,pap.getBirthday());
            map.put(AdvancedPersonExcelModel.culturallevel,pap.getCulturalLevel());
            map.put(AdvancedPersonExcelModel.politicalstatus,pap.getPoliticalStatus());
            map.put(AdvancedPersonExcelModel.owendcityindustry,pap.getOwnedCityIndustry());
            map.put(AdvancedPersonExcelModel.organization,pap.getOrganization());
            map.put(AdvancedPersonExcelModel.duty,pap.getDuty());
            map.put(AdvancedPersonExcelModel.identitycard,pap.getIdentityCard());
            map.put(AdvancedPersonExcelModel.tel,pap.getTel());
            switch (pap.getStatus()){
                case 1:map.put(AdvancedPersonExcelModel.Status,"未认定");break;
                case 2:map.put(AdvancedPersonExcelModel.Status,"新认定");break;
                case 3:map.put(AdvancedPersonExcelModel.Status,"死亡");break;
                case 4:map.put(AdvancedPersonExcelModel.Status,"调动");break;
                case 5:map.put(AdvancedPersonExcelModel.Status,"取消");break;
            }
            map.put(AdvancedPersonExcelModel.statusimformation,pap.getStatusInformation());
            map.put(AdvancedPersonExcelModel.iscurrent,pap.getIsCurrent()==1?"是":"否");
            map.put(AdvancedPersonExcelModel.additiontime,sdf.format(pap.getAdditionTime()));
            map.put(AdvancedPersonExcelModel.physicalcondition,pap.getPhysicalCondition());
            map.put(AdvancedPersonExcelModel.family_difficulties_and_employment,pap.getFamilyDifficultiesAndEmployment());
            map.put(AdvancedPersonExcelModel.outstanding_deed,pap.getOutstandingDeed());
            map.put(AdvancedPersonExcelModel.cmid,pap.getCertificationMaterials().getId());
            map.put(AdvancedPersonExcelModel.grantunit,pap.getCertificationMaterials().getGrantUnit());
            map.put(AdvancedPersonExcelModel.granttime,sdf.format(pap.getCertificationMaterials().getGrantTime()));
            map.put(AdvancedPersonExcelModel.file_name_number,pap.getCertificationMaterials().getFileNameNumber());
            map.put(AdvancedPersonExcelModel.issuing_unit,pap.getCertificationMaterials().getIssuingUnit());
            map.put(AdvancedPersonExcelModel.profile_name,pap.getCertificationMaterials().getProfileName());
            map.put(AdvancedPersonExcelModel.ctreatment,pap.getCertificationMaterials().getTreatment());
            map.put(AdvancedPersonExcelModel.deed_briefing,pap.getCertificationMaterials().getDeedBriefing());
            map.put(AdvancedPersonExcelModel.certification_base,pap.getCertificationMaterials().getCertificationBase());
            map.put(AdvancedPersonExcelModel.opinion,pap.getCertificationMaterials().getOpinion());
        }
        excelUtil.setHeadArray(headArray);
        excelUtil.setBodyList(bodyList);
        excelUtil.writeExcel(file,"先进个人导出结果");
        return file;
    }

    @Override
    @Transactional
    public int save(PAdvancedPerson padvancedPerson) {

        AdvancedPerson advancedPerson=new AdvancedPerson();
        CertificationMaterials certificationMaterials=new CertificationMaterials();
        advancedPerson.setAdditionTime(new Date(System.currentTimeMillis()));
        advancedPerson.setBirthday(padvancedPerson.getBirthday());
        advancedPerson.setCulturalLevel(padvancedPerson.getCulturalLevel());
        advancedPerson.setDuty(padvancedPerson.getDuty());
        advancedPerson.setFamilyDifficultiesAndEmployment(padvancedPerson.getFamilyDifficultiesAndEmployment());
        advancedPerson.setGender(padvancedPerson.getGender());
        advancedPerson.setHonoraryTitle(padvancedPerson.getHonoraryTitle());
        advancedPerson.setId(IDGenerator.generatorUniqueLongId().toString());
        advancedPerson.setIdentityCard(padvancedPerson.getIdentityCard());
        advancedPerson.setIsCurrent(padvancedPerson.getIsCurrent());
        advancedPerson.setName(padvancedPerson.getName());
        advancedPerson.setNation(padvancedPerson.getNation());
        advancedPerson.setOrganization(padvancedPerson.getOrganization());
        advancedPerson.setOutstandingDeed(padvancedPerson.getOutstandingDeed());
        advancedPerson.setOwnedCityIndustry(padvancedPerson.getOwnedCityIndustry());
        advancedPerson.setPhysicalCondition(padvancedPerson.getPhysicalCondition());
        advancedPerson.setPoliticalStatus(padvancedPerson.getPoliticalStatus());
        advancedPerson.setStatus(padvancedPerson.getStatus());
        advancedPerson.setStatusInformation(padvancedPerson.getStatusInformation());
        advancedPerson.setTel(padvancedPerson.getTel());
        advancedPerson.setTreatment(padvancedPerson.getTreatment());
        certificationMaterials.setCertificationBase(padvancedPerson.getCertificationMaterials().getCertificationBase());
        certificationMaterials.setDeedBriefing(padvancedPerson.getCertificationMaterials().getDeedBriefing());
        certificationMaterials.setFileNameNumber(padvancedPerson.getCertificationMaterials().getFileNameNumber());
        certificationMaterials.setGrantTime(padvancedPerson.getCertificationMaterials().getGrantTime());
        certificationMaterials.setGrantUnit(padvancedPerson.getCertificationMaterials().getGrantUnit());
        certificationMaterials.setId(IDGenerator.generatorUniqueLongId().toString());
        certificationMaterials.setIssuingUnit(padvancedPerson.getCertificationMaterials().getIssuingUnit());
        certificationMaterials.setOpinion(padvancedPerson.getCertificationMaterials().getOpinion());
        certificationMaterials.setProfileName(padvancedPerson.getCertificationMaterials().getProfileName());
        certificationMaterials.setTreatment(padvancedPerson.getCertificationMaterials().getTreatment());
        certificationMaterialsDAO.insertSelective(certificationMaterials);
        advancedPerson.setCertificationMaterialsId(certificationMaterials.getId());
        System.out.println(advancedPerson.getCertificationMaterialsId());
        System.out.println(padvancedPerson.getCertificationMaterials().getId());

        int result = advancedPersonDAO.insert(advancedPerson);

        System.out.println(advancedPerson.getId());
        List<HistoryTitle> lh=padvancedPerson.getHistoryTitles();
        for(HistoryTitle historyTitle:lh){

        historyTitle.setApId(advancedPerson.getId());
        System.out.println(advancedPerson.getId());
        historyTitle.setGrantUnit(padvancedPerson.getCertificationMaterials().getGrantUnit());
        historyTitle.setId( IDGenerator.generatorLongId().toString());
        historyTitle.setName(padvancedPerson.getName());
        historyTitle.setObtainDate(padvancedPerson.getAdditionTime());
        historyTitleDAO.insertSelective(historyTitle);}
        return  result;
    }

    @Override
    @Transactional
    public void delete(Section section) {
//        删除历史表

         historyTitleDAO.deleteByAPID(section.getIds().split(","));
//         将cid导出，删除先进个人表的数据
         List<String> cidlist=advancedPersonDAO.selectCid(section.getIds().split(","));
         advancedPersonDAO.deleteByPrimaryKey(section.getIds().split(","));
//         删除申报材料表的数据

        certificationMaterialsDAO.deleteByPrimaryKey(cidlist);


        //修改sql




    }

    @Override
    public ResultModel<PAdvancedPerson> list(RequestList<PAdvancedPerson> rl) {

            //分页查询

           PAdvancedPerson pAdvancedPerson = rl.getKey();


            //初始化对象

            int skip=(rl.getPage()-1)*rl.getRows();
            int rows=rl.getRows();
        List<PAdvancedPerson> list=advancedPersonDAO.dividepageselect(pAdvancedPerson,skip,rows);
            int total=advancedPersonDAO.count(pAdvancedPerson);




        ResultModel<PAdvancedPerson> result = new ResultModel<>((int)total,rows,rl.getPage(),list );

return  result;

    }


    @Override
    @Transactional
    public void update(PAdvancedPerson padvancedPerson) {
//        更新先进个人表
        AdvancedPerson advancedPerson=new AdvancedPerson();
        advancedPerson.setAdditionTime(padvancedPerson.getAdditionTime());
        advancedPerson.setBirthday(padvancedPerson.getBirthday());
        advancedPerson.setCulturalLevel(padvancedPerson.getCulturalLevel());
        advancedPerson.setDuty(padvancedPerson.getDuty());
        advancedPerson.setFamilyDifficultiesAndEmployment(padvancedPerson.getFamilyDifficultiesAndEmployment());
        advancedPerson.setGender(padvancedPerson.getGender());
        advancedPerson.setHonoraryTitle(padvancedPerson.getHonoraryTitle());
        advancedPerson.setId(padvancedPerson.getId());
        advancedPerson.setIdentityCard(padvancedPerson.getIdentityCard());
        advancedPerson.setIsCurrent(padvancedPerson.getIsCurrent());
        advancedPerson.setName(padvancedPerson.getName());
        advancedPerson.setNation(padvancedPerson.getNation());
        advancedPerson.setOrganization(padvancedPerson.getOrganization());
        advancedPerson.setOutstandingDeed(padvancedPerson.getOutstandingDeed());
        advancedPerson.setOwnedCityIndustry(padvancedPerson.getOwnedCityIndustry());
        advancedPerson.setPhysicalCondition(padvancedPerson.getPhysicalCondition());
        advancedPerson.setPoliticalStatus(padvancedPerson.getPoliticalStatus());
        advancedPerson.setStatus(padvancedPerson.getStatus());
        advancedPerson.setStatusInformation(padvancedPerson.getStatusInformation());
        advancedPerson.setTel(padvancedPerson.getTel());
        advancedPerson.setTreatment(padvancedPerson.getTreatment());
        advancedPerson.setCertificationMaterialsId(padvancedPerson.getCertificationMaterials().getId());

        advancedPersonDAO.updateByPrimaryKey(advancedPerson);
//      更新申请材料表
        CertificationMaterials certificationMaterials=new CertificationMaterials();
        certificationMaterials.setCertificationBase(padvancedPerson.getCertificationMaterials().getCertificationBase());
        certificationMaterials.setDeedBriefing(padvancedPerson.getCertificationMaterials().getDeedBriefing());
        certificationMaterials.setFileNameNumber(padvancedPerson.getCertificationMaterials().getFileNameNumber());
        certificationMaterials.setGrantTime(padvancedPerson.getCertificationMaterials().getGrantTime());
        certificationMaterials.setGrantUnit(padvancedPerson.getCertificationMaterials().getGrantUnit());
        certificationMaterials.setId(padvancedPerson.getCertificationMaterials().getId());
        certificationMaterials.setIssuingUnit(padvancedPerson.getCertificationMaterials().getIssuingUnit());
        certificationMaterials.setOpinion(padvancedPerson.getCertificationMaterials().getOpinion());
        certificationMaterials.setProfileName(padvancedPerson.getCertificationMaterials().getProfileName());
        certificationMaterials.setTreatment(padvancedPerson.getCertificationMaterials().getTreatment());
        certificationMaterialsDAO.updateByPrimaryKey(certificationMaterials);
//        更新历史
        List<HistoryTitle> lh=padvancedPerson.getHistoryTitles();
        for(HistoryTitle historyTitle:lh){
        historyTitle.setApId(padvancedPerson.getId());
        historyTitle.setGrantUnit(padvancedPerson.getCertificationMaterials().getGrantUnit());
        historyTitle.setId( IDGenerator.generatorLongId().toString());
        historyTitle.setName(padvancedPerson.getName());
        historyTitle.setObtainDate(padvancedPerson.getAdditionTime());
        historyTitleDAO.updateByPrimaryKey(historyTitle);}



    }

    @Override
    public List<Map<String, ?>> statics(Section section) {
        List<Map<String,?>> list = advancedPersonDAO.statics(section);
        try {
            StatisticsResult collectiveStatisticsResult = StatisticsResult.getCollectiveStatisticsResult(list);
            srDao.insert(collectiveStatisticsResult);
        } catch (JsonProcessingException e) {
            throw new DataViolationException(104,"List转String异常");
        }
        return list;
    }


    @Override
    @Transactional
    public void recognise(List<AdvancedPerson> acList) {
        for (AdvancedPerson advancedPerson : acList) {
            advancedPersonDAO.upstatus(advancedPerson.getId(),advancedPerson.getStatus());
        }

    }

    @Override
    @Transactional
    public void overdue(Section section) {
        advancedPersonDAO.overdue(section.getIds().split((",")));


    }
}