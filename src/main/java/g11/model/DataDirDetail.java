package g11.model;

public class DataDirDetail {
    private Integer id;

    private String content;

    private Integer dataDir;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getDataDir() {
        return dataDir;
    }

    public void setDataDir(Integer dataDir) {
        this.dataDir = dataDir;
    }
}