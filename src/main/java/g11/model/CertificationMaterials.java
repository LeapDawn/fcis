package g11.model;

import java.util.Date;

public class CertificationMaterials {
    private String id;

    private String grantUnit;

    private Date grantTime;

    private String fileNameNumber;

    private String issuingUnit;

    private String profileName;

    private String treatment;

    private String deedBriefing;

    private String certificationBase;

    private String opinion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGrantUnit() {
        return grantUnit;
    }

    public void setGrantUnit(String grantUnit) {
        this.grantUnit = grantUnit == null ? null : grantUnit.trim();
    }

    public Date getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(Date grantTime) {
        this.grantTime = grantTime;
    }

    public String getFileNameNumber() {
        return fileNameNumber;
    }

    public void setFileNameNumber(String fileNameNumber) {
        this.fileNameNumber = fileNameNumber == null ? null : fileNameNumber.trim();
    }

    public String getIssuingUnit() {
        return issuingUnit;
    }

    public void setIssuingUnit(String issuingUnit) {
        this.issuingUnit = issuingUnit == null ? null : issuingUnit.trim();
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName == null ? null : profileName.trim();
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment == null ? null : treatment.trim();
    }

    public String getDeedBriefing() {
        return deedBriefing;
    }

    public void setDeedBriefing(String deedBriefing) {
        this.deedBriefing = deedBriefing == null ? null : deedBriefing.trim();
    }

    public String getCertificationBase() {
        return certificationBase;
    }

    public void setCertificationBase(String certificationBase) {
        this.certificationBase = certificationBase == null ? null : certificationBase.trim();
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
    }
}