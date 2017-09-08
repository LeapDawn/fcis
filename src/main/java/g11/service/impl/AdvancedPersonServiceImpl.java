package g11.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import g11.commons.excelModel.AdvancedPersonExcelModel;
import g11.commons.exception.DataViolationException;
import g11.commons.exception.ExcelException;
import g11.commons.util.ExcelUtil;
import g11.commons.util.FileUtil;
import g11.commons.util.IDGenerator;
import g11.commons.util.StringUtil;
import g11.dao.*;
import g11.dto.RequestList;
import g11.dto.ResultModel;
import g11.dto.UploadResultModel;
import g11.dto.pageModel.PAdvancedPerson;
import g11.dto.pageModel.Section;
import g11.model.*;
import g11.service.AdvancedPersonService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 接口的实现类
 */
@Service
public class AdvancedPersonServiceImpl implements AdvancedPersonService {

    @Autowired
    private AdvancedPersonDAO advancedPersonDAO;
    @Autowired
    private CertificationMaterialsDAO certificationMaterialsDAO;
    @Autowired
    private HistoryTitleDAO historyTitleDAO;
    @Autowired
    private StatisticsResultDAO srDao;
    @Autowired
    private DataDirDetailDAO dataDirDetailDAO;

    @Override
    public File downloadData(Section section, File file) throws ExcelException {
        FileUtil fileUtil = new FileUtil();

        List<PAdvancedPerson> lap = advancedPersonDAO.selectByPrimaryKeys(section.getIds().split(","), section.getIsCurrent());
        getFile(lap, file);
        return file;
    }


    @Override
    public UploadResultModel importData(InputStream input, String user) throws ExcelException {
        ExcelUtil excelUtil = new ExcelUtil();
        List<Map<String, Object>> bodyList = excelUtil.readExcel(input, AdvancedPersonExcelModel.getinputAdvancedPersonExcelModel());
        PAdvancedPerson pa = null;
        UploadResultModel uploadResultModel = new UploadResultModel();
        List<PAdvancedPerson> data = new ArrayList<PAdvancedPerson>(); // 导入失败数据
        List<DataDirDetail> statusList = dataDirDetailDAO.selectByDataDir(2);
        List<DataDirDetail> treatmentList = dataDirDetailDAO.selectByDataDir(3);
        boolean error_flag = false;
        for (Map<String, Object> map : bodyList) {
            error_flag = false;
            pa = new PAdvancedPerson();
            pa.setId((String) map.get(AdvancedPersonExcelModel.ID));
            pa.setHonoraryTitle((String) map.get(AdvancedPersonExcelModel.honorary_title));
            String h = (String) map.get(AdvancedPersonExcelModel.treatment);
            for (DataDirDetail dataDirDetail : treatmentList) {
                if (dataDirDetail.getContent().equals(h)) {
                    pa.setTreatment(dataDirDetail);
                }
            }
            if (pa.getTreatment() == null) {
                pa.setError("该待遇输入不规范");
                DataDirDetail ddd = new DataDirDetail();
                ddd.setContent(h);
                pa.setTreatment(ddd);
                error_flag = true;
            }
            pa.setName((String) map.get(AdvancedPersonExcelModel.name));
            if ((String) map.get(AdvancedPersonExcelModel.gender) == "男")
                pa.setGender((byte) 1);
            else
                pa.setGender((byte) 0);
            pa.setNation((String) map.get(AdvancedPersonExcelModel.nation));
            try {
                pa.setBirthday(StringUtil.parseDate((String)map.get(AdvancedPersonExcelModel.birthday)));
            } catch (ParseException e) {
                pa.setError("授予日期格式不正确，正确示例:2017-01-01");
                error_flag = true;
            }
            pa.setCulturalLevel((String) map.get(AdvancedPersonExcelModel.culturallevel));
            pa.setPoliticalStatus((String) map.get(AdvancedPersonExcelModel.politicalstatus));
            String s = (String) map.get(AdvancedPersonExcelModel.owendcityindustry);
            if (s == null || s.trim().equals("") || !AdvancedPersonExcelModel.checkLocation(s)) {
                pa.setError("市区信息错误:" + AdvancedPersonExcelModel.getLocations());
                pa.setOwnedCityIndustry(s != null ? s : "");
                error_flag = true;
            } else {
                pa.setOwnedCityIndustry(s);
            }
            pa.setOrganization((String) map.get(AdvancedPersonExcelModel.organization));
            pa.setDuty((String) map.get(AdvancedPersonExcelModel.duty));
            pa.setIdentityCard((String) map.get(AdvancedPersonExcelModel.identitycard));
            pa.setTel((String) map.get(AdvancedPersonExcelModel.tel));
            for (DataDirDetail dataDirDetail : statusList) {
                if (dataDirDetail.getContent().equals("未认定")) {
                    pa.setStatus(dataDirDetail);
                    break;
                }
            }
            pa.setStatusInformation((String) map.get(AdvancedPersonExcelModel.statusimformation));
            pa.setPhysicalCondition((String) map.get(AdvancedPersonExcelModel.physicalcondition));
            pa.setFamilyDifficultiesAndEmployment((String) map.get(AdvancedPersonExcelModel.family_difficulties_and_employment));
            pa.setOutstandingDeed((String) map.get(AdvancedPersonExcelModel.outstanding_deed));
            CertificationMaterials c = new CertificationMaterials();
            c.setId((String) map.get(AdvancedPersonExcelModel.cmid));
            c.setGrantUnit((String) map.get(AdvancedPersonExcelModel.grantunit));
            try {
                c.setGrantTime(StringUtil.parseDate((String) map.get(AdvancedPersonExcelModel.granttime)));
            } catch (ParseException e) {
                pa.setError("认定材料日期格式不正确，正确示例:2017-01-01");
                error_flag = true;
            }
            c.setFileNameNumber((String) map.get(AdvancedPersonExcelModel.file_name_number));
            c.setIssuingUnit((String) map.get(AdvancedPersonExcelModel.issuing_unit));
            c.setProfileName((String) map.get(AdvancedPersonExcelModel.profile_name));
            c.setTreatment((String) map.get(AdvancedPersonExcelModel.ctreatment));
            c.setDeedBriefing((String) map.get(AdvancedPersonExcelModel.deed_briefing));
            c.setCertificationBase((String) map.get(AdvancedPersonExcelModel.certification_base));
            c.setOpinion((String) map.get(AdvancedPersonExcelModel.opinion));
            pa.setCertificationMaterials(c);
            HistoryTitle his = new HistoryTitle();
            String hisgrantunit = (String) map.get(AdvancedPersonExcelModel.hisgrantunit);
            String obtaindate = (String) map.get(AdvancedPersonExcelModel.obtaindate);
            String name = (String) map.get(AdvancedPersonExcelModel.historytitlename);
            List<HistoryTitle> ll = new ArrayList<>();
            if (hisgrantunit != null && obtaindate != null && name!= null
                    && !hisgrantunit.trim().equals("") && !obtaindate.trim().equals("") && !name.trim().equals("")) {
                String[] hisgrantunits = hisgrantunit.split(AdvancedPersonExcelModel.split);
                String[] obtaidates = obtaindate.split(AdvancedPersonExcelModel.split);
                String[] names = name.split(AdvancedPersonExcelModel.split);
                if(hisgrantunits.length != names.length) {
                    pa.setError("历史荣誉称号中称号名称和授予单位数量不对应");
                    error_flag = true;
                } else {
                    for (int i = 0; i < names.length; i++) {
                        HistoryTitle ht = new HistoryTitle();
                        his.setName(names[i]);
                        his.setGrantUnit(hisgrantunits[i]);
                        try {
                            his.setObtainDate(StringUtil.parseDate(obtaidates[i]));
                        } catch (ParseException e) {
                            pa.setError("日期格式不正确，正确示例:2017-01-01");
                            error_flag = true;
                            break;
                        }
                        ll.add(his);
                    }
                }
            }
            pa.setHistoryTitles(ll);
            pa.setIsCurrent((byte)1);
            if (error_flag) {
                data.add(pa);
            } else if (this.save(pa) == 0) {
                data.add(pa);
            }
        }
        uploadResultModel.setTotal(bodyList.size());
        uploadResultModel.setError(data.size());
        uploadResultModel.setSuccess(bodyList.size() - data.size());
        uploadResultModel.setData(data);
        return uploadResultModel;
    }

    @Override
    public File exportData(Section section, File file) throws ExcelException {
        ExcelUtil excelUtil = new ExcelUtil();
        List<PAdvancedPerson> alist = advancedPersonDAO.selectByDATE(section);
        getFile(alist, file);
        return file;
    }

    @Override
    @Transactional
    public int save(PAdvancedPerson padvancedPerson) {

        AdvancedPerson advancedPerson = new AdvancedPerson();
        CertificationMaterials certificationMaterials = new CertificationMaterials();
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
        advancedPerson.setStatus(padvancedPerson.getStatus().getId());
        advancedPerson.setStatusInformation(padvancedPerson.getStatusInformation());
        advancedPerson.setTel(padvancedPerson.getTel());
        advancedPerson.setTreatment(padvancedPerson.getTreatment().getId());
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
        List<HistoryTitle> lh = padvancedPerson.getHistoryTitles();
        for (HistoryTitle historyTitle : lh) {
            historyTitle.setApId(advancedPerson.getId());
            historyTitle.setId(IDGenerator.generatorLongId().toString());

            historyTitleDAO.insertSelective(historyTitle);
        }
        return result;
    }

    @Override
    @Transactional
    public void delete(Section section) {
//        删除历史表

        historyTitleDAO.deleteByAPID(section.getIds().split(","));
//         将cid导出，删除先进个人表的数据
        List<String> cidlist = advancedPersonDAO.selectCid(section.getIds().split(","));
        advancedPersonDAO.deleteByPrimaryKey(section.getIds().split(","));
//         删除申报材料表的数据
        for (String p : cidlist) {
            certificationMaterialsDAO.deleteByPrimaryKey(p);
        }

        //修改sql


    }

    @Override
    public ResultModel<PAdvancedPerson> list(RequestList<PAdvancedPerson> rl) {
        //分页查询
        PAdvancedPerson pAdvancedPerson = rl.getKey();
        //初始化对象
        int total = advancedPersonDAO.count(pAdvancedPerson);
        ResultModel<PAdvancedPerson> result = new ResultModel<>((int) total, rl.getRows(), rl.getPage());
        List<PAdvancedPerson> list = advancedPersonDAO.dividepageselect(pAdvancedPerson, (result.getCurrentPage() - 1) * result.getRows(), result.getRows());
         result.setData(list);
        return result;
    }


    @Override
    @Transactional
    public void update(PAdvancedPerson padvancedPerson) {
//        更新先进个人表
        AdvancedPerson advancedPerson = new AdvancedPerson();
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
        if (padvancedPerson.getStatus() != null) {
            advancedPerson.setStatus(padvancedPerson.getStatus().getId());
        }
        advancedPerson.setStatusInformation(padvancedPerson.getStatusInformation());
        advancedPerson.setTel(padvancedPerson.getTel());
        advancedPerson.setTreatment(padvancedPerson.getTreatment().getId());
        advancedPerson.setCertificationMaterialsId(padvancedPerson.getCertificationMaterials().getId());

        advancedPersonDAO.updateByPrimaryKeySelective(advancedPerson);
//      更新申请材料表
        CertificationMaterials certificationMaterials = new CertificationMaterials();
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
        certificationMaterialsDAO.updateByPrimaryKeySelective(certificationMaterials);
//        更新历史
        List<HistoryTitle> lh = padvancedPerson.getHistoryTitles();
        for (HistoryTitle historyTitle : lh) {
            historyTitleDAO.updateByPrimaryKeySelective(historyTitle);
        }


    }

    @Override
    public List<Map<String, ?>> statics(Section section) {
        List<Map<String, ?>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("type", "荣誉称号");
        map.put("value", advancedPersonDAO.staticsByTitle(section));
        list.add(map);
        map = new HashMap<>();
        map.put("type", "地州市");
        map.put("value", advancedPersonDAO.staticsByCity(section));
        list.add(map);
        map = new HashMap<>();
        map.put("type", "民族");
        map.put("value", advancedPersonDAO.staticsByNation(section));
        list.add(map);
        try {
            StatisticsResult collectiveStatisticsResult = StatisticsResult.getPersonStatisticsResult(list);
            srDao.insert(collectiveStatisticsResult);
        } catch (JsonProcessingException e) {
            throw new DataViolationException(204, "统计失败，数据出错");
        }
        return list;
    }


    @Override
    @Transactional
    public void recognise(List<AdvancedPerson> acList) {
        for (AdvancedPerson advancedPerson : acList) {
            advancedPersonDAO.upstatus(advancedPerson.getId(), advancedPerson.getStatus(), advancedPerson.getStatusInformation());
        }
    }

    @Override
    @Transactional
    public void overdue(Section section) {
        advancedPersonDAO.overdue(section.getIds().split((",")));
    }

    @Override
    public StatisticsResult getLastStaticsResult() {
        return srDao.selectLastPerson();
    }

    private void getFile(List<PAdvancedPerson> lap, File file) throws ExcelException {
        ExcelUtil excelUtil = new ExcelUtil();
        String[] headArray = AdvancedPersonExcelModel.getoutputAdvancedPersonExcelModel();
        List<Map<String, Object>> bodyList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < lap.size(); i++) {
            System.out.println(lap.get(i));
        }
        for (PAdvancedPerson ppp : lap) {
            System.out.println(ppp.getCertificationMaterials());
        }
        Map<String, Object> map = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (PAdvancedPerson pap : lap) {

            map = new HashMap<String, Object>();
            map.put(AdvancedPersonExcelModel.ID, pap.getId());
            map.put(AdvancedPersonExcelModel.honorary_title, pap.getHonoraryTitle());
            if (pap.getTreatment() != null) {
                map.put(AdvancedPersonExcelModel.treatment, pap.getTreatment().getContent());
            } else {
                map.put(AdvancedPersonExcelModel.treatment, "");
            }
            map.put(AdvancedPersonExcelModel.name, pap.getName());
            map.put(AdvancedPersonExcelModel.gender, pap.getGender() == 1 ? "男" : "女");
            map.put(AdvancedPersonExcelModel.nation, pap.getNation());
            map.put(AdvancedPersonExcelModel.birthday, sdf.format(pap.getBirthday()));
            map.put(AdvancedPersonExcelModel.culturallevel, pap.getCulturalLevel());
            map.put(AdvancedPersonExcelModel.politicalstatus, pap.getPoliticalStatus());
            map.put(AdvancedPersonExcelModel.owendcityindustry, pap.getOwnedCityIndustry());
            map.put(AdvancedPersonExcelModel.organization, pap.getOrganization());
            map.put(AdvancedPersonExcelModel.duty, pap.getDuty());
            map.put(AdvancedPersonExcelModel.identitycard, pap.getIdentityCard());
            map.put(AdvancedPersonExcelModel.tel, pap.getTel());
            if (pap.getStatus() != null) {
                map.put(AdvancedPersonExcelModel.Status, pap.getStatus().getContent());
            } else {
                map.put(AdvancedPersonExcelModel.Status, "");
            }
            map.put(AdvancedPersonExcelModel.statusimformation, pap.getStatusInformation());

            map.put(AdvancedPersonExcelModel.iscurrent, pap.getIsCurrent() == 1 ? "是" : "否");
            if (pap.getAdditionTime() != null) {
                map.put(AdvancedPersonExcelModel.additiontime, sdf.format(pap.getAdditionTime()));
            }
            map.put(AdvancedPersonExcelModel.physicalcondition, pap.getPhysicalCondition());
            map.put(AdvancedPersonExcelModel.family_difficulties_and_employment, pap.getFamilyDifficultiesAndEmployment());
            map.put(AdvancedPersonExcelModel.outstanding_deed, pap.getOutstandingDeed());
            if (pap.getCertificationMaterials() != null) {
                map.put(AdvancedPersonExcelModel.cmid, pap.getCertificationMaterials().getId());
                map.put(AdvancedPersonExcelModel.grantunit, pap.getCertificationMaterials().getGrantUnit());
                map.put(AdvancedPersonExcelModel.granttime, sdf.format(pap.getCertificationMaterials().getGrantTime()));
                map.put(AdvancedPersonExcelModel.file_name_number, pap.getCertificationMaterials().getFileNameNumber());
                map.put(AdvancedPersonExcelModel.issuing_unit, pap.getCertificationMaterials().getIssuingUnit());
                map.put(AdvancedPersonExcelModel.profile_name, pap.getCertificationMaterials().getProfileName());
                map.put(AdvancedPersonExcelModel.ctreatment, pap.getCertificationMaterials().getTreatment());
                map.put(AdvancedPersonExcelModel.deed_briefing, pap.getCertificationMaterials().getDeedBriefing());
                map.put(AdvancedPersonExcelModel.certification_base, pap.getCertificationMaterials().getCertificationBase());
                map.put(AdvancedPersonExcelModel.opinion, pap.getCertificationMaterials().getOpinion());
            }
            if (pap.getHistoryTitles() == null || pap.getHistoryTitles().size() == 0) {
                bodyList.add(map);
                map = new HashMap<String, Object>();
            } else {
                for (HistoryTitle historyTitle : pap.getHistoryTitles()) {
                    System.out.println(historyTitle);
                    map.put(AdvancedPersonExcelModel.historytitleid, historyTitle.getId());
                    map.put(AdvancedPersonExcelModel.historytitlename, historyTitle.getName());
                    map.put(AdvancedPersonExcelModel.hisgrantunit, historyTitle.getGrantUnit());
                    map.put(AdvancedPersonExcelModel.obtaindate, sdf.format(historyTitle.getObtainDate()));
                    bodyList.add(map);
                    map = new HashMap<String, Object>();
                }
            }
        }
        excelUtil.setHeadArray(headArray);
        excelUtil.setBodyList(bodyList);
        excelUtil.writeExcel(file, "先进个人查询结果结果");
    }

}