package g11.commons.excelModel;



/**
 * Created by Administrator on 2017/8/14.
 */
public class AdvancedPersonExcelModel {
    public static final String ID="先进个人编号";
    public static final String honorary_title="劳模荣誉称号";
    public static final String treatment="享受待遇";
    public static final String name="劳模姓名";
    public static final String gender="劳模性别";
    public static final String nation="名族";
    public static final String birthday="生日";
    public static final String culturallevel="文化程度";
    public static final String politicalstatus="政治面貌";
    public static final String owendcityindustry="所属州市";
    public static final String organization="工作单位";
    public static final String duty="职务";
    public static final String identitycard="身份证号";
    public static final String tel="电话号码";
    public static final String Status="劳模状态";
    public static final String statusimformation="状态信息";
    public static final String iscurrent="是否为当界劳模";
    public static final String additiontime="添加时间";
    public static final String physicalcondition="身体状况";
    public static final String family_difficulties_and_employment="家庭困难和就业情况";
    public static final String outstanding_deed="主要突出事迹";
    public static final String cmid="授予材料编号";
    public static final String grantunit="授予单位";
    public static final String granttime="授予时间";
    public static final String file_name_number="表彰决定文件和文件号";
    public static final String issuing_unit="发文单位";
    public static final String profile_name="资料名称";
    public static final String ctreatment="提供档案，表彰决定等原始资料明确的荣誉称号待遇";
    public static final String deed_briefing="当时评选的主要突出事迹简述";
    public static final String certification_base="认定依据";
    public static final String opinion="初审认定意见";
    public static final String historytitleid="历史荣誉称号编号";
    public static final String historytitlename="历史荣誉称号名";
    public static final String obtaindate="发放日期";
    public static final String hisgrantunit="发放单位";
    public static final String split=";";

    public static final String[] locations = new String[]{"越秀区","荔湾区","海珠区","天河区","白云区","黄浦区","番禺区","花都区","南沙区","增城","从化"};

    public static boolean checkLocation(String location) {
        for (String s : locations) {
            if (s.equals(location)) {
                return true;
            }
        }
        return false;
    }

    public static String getLocations() {
        StringBuffer sb = new StringBuffer("[");
        for (String s : locations) {
            sb.append(s);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');
        return sb.toString();
    }


    public static String[] getoutputAdvancedPersonExcelModel(){
        return new String[]{
                ID,honorary_title,treatment,name,gender,nation,birthday,culturallevel,politicalstatus,owendcityindustry,
                organization,duty,identitycard,tel,Status,iscurrent,additiontime,physicalcondition,family_difficulties_and_employment,
                outstanding_deed,cmid,grantunit,granttime,file_name_number,issuing_unit,profile_name,ctreatment,deed_briefing,certification_base,
                opinion,historytitleid,historytitlename,obtaindate,hisgrantunit
        };
    }
    public static String[] getinputAdvancedPersonExcelModel(){
        return new String[]{honorary_title,treatment,name,gender,nation,birthday,culturallevel,politicalstatus,owendcityindustry,
                organization,duty,identitycard,tel,physicalcondition,family_difficulties_and_employment,
                outstanding_deed,cmid,grantunit,granttime,file_name_number,issuing_unit,profile_name,ctreatment,deed_briefing,certification_base,
                opinion,historytitleid,historytitlename,obtaindate,hisgrantunit};
    }
}
