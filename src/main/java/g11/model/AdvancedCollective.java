package g11.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdvancedCollective {
    private String id;

    private String name;

    private String honoraryTitle;

    private String ownedCityIndustry;

    private String principalName;

    private String contactDetail;

    private String outstandingDeed;

    private Byte isCurrent;

    private Date additionTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getHonoraryTitle() {
        return honoraryTitle;
    }

    public void setHonoraryTitle(String honoraryTitle) {
        this.honoraryTitle = honoraryTitle == null ? null : honoraryTitle.trim();
    }

    public String getOwnedCityIndustry() {
        return ownedCityIndustry;
    }

    public void setOwnedCityIndustry(String ownedCityIndustry) {
        this.ownedCityIndustry = ownedCityIndustry == null ? null : ownedCityIndustry.trim();
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName == null ? null : principalName.trim();
    }

    public String getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(String contactDetail) {
        this.contactDetail = contactDetail == null ? null : contactDetail.trim();
    }

    public String getOutstandingDeed() {
        return outstandingDeed;
    }

    public void setOutstandingDeed(String outstandingDeed) {
        this.outstandingDeed = outstandingDeed == null ? null : outstandingDeed.trim();
    }

    public Byte getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Byte isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Date getAdditionTime() {
        return additionTime;
    }

    public void setAdditionTime(Date additionTime) {
        this.additionTime = additionTime;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return "AdvancedCollective{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", honoraryTitle='" + honoraryTitle + '\'' +
                ", ownedCityIndustry='" + ownedCityIndustry + '\'' +
                ", principalName='" + principalName + '\'' +
                ", contactDetail='" + contactDetail + '\'' +
                ", outstandingDeed='" + outstandingDeed + '\'' +
                ", isCurrent=" + isCurrent +
                ", additionTime=" + sdf.format(additionTime) +
                '}';
    }
}