package g11.commons.excelModel;

/**
 * Created by Administrator on 2017/8/10.

 */
public class AdvancedCollectiveExcelModel {
    public static final String ID = "编号";
    public static final String NAME = "单位名称";
    public static final String HONORARY_TITLE = "荣誉称号";
    public static final String OWNED_CITY_INDUSTRY = "所属市州产业";
    public static final String PRINCIPAL_NAME = "负责人名称";
    public static final String CONTACT_DETAIL = "联系方式";
    public static final String OUTSTANDING_DEED = "主要突出事迹";
    public static final String IS_CURRENT = "是否为当届劳模";
    public static final String ADDITION_TIME = "新增该记录的时间";

    public static String[] getOutputAdvancedCollectiveExcelModel(){
        return new String[]{ID,NAME,HONORARY_TITLE,OWNED_CITY_INDUSTRY,PRINCIPAL_NAME,
                CONTACT_DETAIL,OUTSTANDING_DEED,IS_CURRENT,ADDITION_TIME};
    }

    public static String[] getInputAdvancedCollectiveExcelModel(){
        return new String[]{NAME,HONORARY_TITLE,OWNED_CITY_INDUSTRY,PRINCIPAL_NAME,
                CONTACT_DETAIL,OUTSTANDING_DEED};
    }
}
