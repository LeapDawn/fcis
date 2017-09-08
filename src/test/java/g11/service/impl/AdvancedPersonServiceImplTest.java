//package g11.service.impl;
//
//import g11.Application;
//import g11.commons.util.IDGenerator;
//import g11.commons.util.StringUtil;
//import g11.dto.RequestList;
//import g11.dto.pageModel.PAdvancedPerson;
//import g11.dto.pageModel.Section;
//import g11.model.CertificationMaterials;
//import g11.model.HistoryTitle;
//import lombok.Setter;
//import org.apache.tomcat.util.http.fileupload.FileUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.File;
//import java.security.PrivateKey;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
///**
// * Created by Administrator on 2017/8/9.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//public class AdvancedPersonServiceImplTest {
//
//    @Setter
//    @Autowired
//    private AdvancedPersonServiceImpl advancedPersonService;
//
//    @Test
//    public void downloadData1() throws Exception {
//
//        File file = new File("D:\\ac.xlsx");
//        file.createNewFile();
//        Section section=new Section();
//        section.setIds("28553827117687");
//        section.setIsCurrent((byte)0);
//        section.setBeginDate(StringUtil.parseDate("2017-08-15"));
//        section.setEndDate(StringUtil.parseDate("2017-08-16"));
//        advancedPersonService.downloadData(section,file);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDispositionFormData("attachment","ac.xlsx");
//
//    }
//
//    @Test
//    public void importData1() throws Exception {
//    }
//
//    @Test
//    public void exportData1() throws Exception {
//    }
//
//    @Test
//    public void save1() throws Exception {
//        PAdvancedPerson ad=new PAdvancedPerson();
//        CertificationMaterials c=new CertificationMaterials();
//        HistoryTitle his=new HistoryTitle();
//        ad.setStatus(1);
//        ad.setStatusInformation("no");
//        ad.setOutstandingDeed("帮助弱小");
//        ad.setFamilyDifficultiesAndEmployment("富裕 ");
//        ad.setPhysicalCondition("健康");
//        ad.setTel("122331213");
//        ad.setIdentityCard("999919919119a1129");
//        ad.setDuty("gongzuo");
//        ad.setOrganization("南海科技有限公司");
//        ad.setOwnedCityIndustry("广州");
//        ad.setPoliticalStatus("群众");
//        ad.setCulturalLevel("本科");
//        ad.setBirthday(new Date());
//        ad.setNation("汉");
//        ad.setGender((byte)1);
//        ad.setName("l");
//        ad.setTreatment(8);
//        ad.setHonoraryTitle("武汉敬业代表");
//        ad.setIsCurrent((byte)1);
//        c.setOpinion("henaho");
//        c.setCertificationBase("dddd");
//        c.setDeedBriefing("e");
//        c.setTreatment("go");
//        c.setProfileName("go");
//        c.setIssuingUnit("go");
//        c.setFileNameNumber("1122");
//        c.setGrantTime(new Date());
//        his.setObtainDate(new Date());
//        his.setName("伟大领袖");
//        his.setGrantUnit("wo");
//        List<HistoryTitle> lh=new ArrayList<>();
//        lh.add(his);
//
//        ad.setHistoryTitles(lh);
//
//
//        ad.setCertificationMaterials(c);
//        advancedPersonService.save(ad);
//    }
//
//    @Test
//    public void delete1() throws Exception {
//
//        Section section=new Section();
//        section.setBeginDate(null);
//        section.setEndDate(null);
//        section.setIsCurrent((byte)1);
//        section.setIds("27877014763211");
//
//        advancedPersonService.delete(section);
//    }
//
//    @Test
//    public void list1() throws Exception {
//        PAdvancedPerson ad=new PAdvancedPerson();
//        ad.setStatus(1);
//        ad.setStatusInformation("yes");
//        ad.setOutstandingDeed("帮助弱小");
//        ad.setFamilyDifficultiesAndEmployment("贫穷");
//        ad.setPhysicalCondition("健康");
//        ad.setTel("122331213");
//        ad.setIdentityCard("999919919119a1129");
//        ad.setDuty("gongzuo");
//        ad.setOrganization("南海科技有限公司");
//        ad.setOwnedCityIndustry("广州");
//        ad.setPoliticalStatus("群众");
//        ad.setCulturalLevel("本科");
//        ad.setBirthday(new Date());
//        ad.setNation("汉");
//        ad.setGender((byte)1);
//        ad.setName("l");
//        ad.setTreatment((int)8);
//        ad.setHonoraryTitle("武汉敬业代表");
//        ad.setIsCurrent((byte)1);
//        RequestList<PAdvancedPerson> requestList=new RequestList<PAdvancedPerson>();
//        requestList.setKey(ad);
//        requestList.setPage(2);
//        requestList.setRows(3);
//        advancedPersonService.list(requestList);
//    }
//
//    @Test
//    public void update1() throws Exception {
//        PAdvancedPerson ad=new PAdvancedPerson();
//        CertificationMaterials c=new CertificationMaterials();
//        HistoryTitle his=new HistoryTitle();
//        ad.setId("28550912547480");
//        ad.setStatus(1);
//        ad.setStatusInformation("yes");
//        ad.setOutstandingDeed("欺负弱小");
//        ad.setFamilyDifficultiesAndEmployment("贫穷");
//        ad.setPhysicalCondition("健康");
//        ad.setTel("122331213");
//        ad.setIdentityCard("999919919119a1129");
//        ad.setDuty("gongzuo");
//        ad.setOrganization("南海科技有限公司");
//        ad.setOwnedCityIndustry("广州");
//        ad.setPoliticalStatus("群众");
//        ad.setCulturalLevel("本科");
//        ad.setBirthday(new Date());
//        ad.setNation("汉");
//        ad.setGender((byte)1);
//        ad.setName("l");
//        ad.setTreatment(8);
//        ad.setHonoraryTitle("武汉敬业代表");
//        ad.setIsCurrent((byte)1);
//
//        c.setOpinion("henaho");
//        c.setCertificationBase("dddd");
//        c.setDeedBriefing("e");
//        c.setTreatment("go");
//        c.setProfileName("go");
//        c.setIssuingUnit("go");
//        c.setFileNameNumber("1122");
//        c.setGrantTime(new Date());
//        his.setObtainDate(new Date());
//        his.setName("伟大领袖");
//        his.setGrantUnit("wo");
//        List<HistoryTitle> lh=new ArrayList<>();
//        lh.add(his);
//
//        ad.setHistoryTitles(lh);
//
//
//        ad.setCertificationMaterials(c);
//        advancedPersonService.update(ad);
//
//    }
//
//    @Test
//    public void statics1() throws Exception {
//    }
//
//    @Test
//    public void recognise1() throws Exception {
//    }
//
//    @Test
//    public void overdue1() throws Exception {
//    }
//
//
//    public static void main(String[] args){
//
//    }
//}