package g11.dto.pageModel;

import g11.model.CertificationMaterials;
import g11.model.HistoryTitle;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
@Data
public class PAdvancedPerson {
//    private String id;
//
//    private String honoraryTitle;
//
//    private Integer treatment;
//
//    private String name;
//
//    private Byte gender;
//
//    private String nation;
//
//    private Date birthday;
//
//    private String culturalLevel;
//
//    private String politicalStatus;
//
//    private String ownedCityIndustry;
//
//    private String organization;
//
//    private String duty;
//
//    private String identityCard;
//
//    private String tel;
//
//    private Integer status;
//
//    private String statusInformation;
//
//    private Byte isCurrent;
//
//    private Date additionTime;
//
//    private String physicalCondition;
//
//    private String familyDifficultiesAndEmployment;
//
//    private String outstandingDeed;

    private String certificationMaterialsId;

    private String id;

    private String honoraryTitle;

    private Integer treatment;

    private String name;

    private Byte gender;

    private String nation;

    private Date birthday;

    private String culturalLevel;

    private String politicalStatus;

    private String ownedCityIndustry;

    private String organization;

    private String duty;

    private String identityCard;

    private String tel;

    private Integer status;

    private String statusInformation;

    private Byte isCurrent;

    private Date additionTime;

    private String physicalCondition;

    private String familyDifficultiesAndEmployment;

    private String outstandingDeed;



    private CertificationMaterials certificationMaterials;

    private List<HistoryTitle> historyTitles;
}
