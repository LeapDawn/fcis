package g11.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class StatisticsResult {

    private final static Byte TYPE_PERSON = 0;
    private final static Byte TYPE_COLLECTIVE = 1;

    private Integer id;

    private String resultContext;

    private Date createDate;

    private Byte statisticsType;

    public static StatisticsResult getPersonStatisticsResult(List list) throws JsonProcessingException {
        return init(new ObjectMapper().writeValueAsString(list), TYPE_PERSON);
    }

    public static StatisticsResult getPersonStatisticsResult(Map map) throws JsonProcessingException {
        return init(new ObjectMapper().writeValueAsString(map), TYPE_PERSON);
    }

    public static StatisticsResult getPersonStatisticsResult(String jsonStr) {
        return init(jsonStr, TYPE_PERSON);
    }

    public static StatisticsResult getCollectiveStatisticsResult(List list) throws JsonProcessingException {
        return init(new ObjectMapper().writeValueAsString(list), TYPE_COLLECTIVE);
    }

    public static StatisticsResult getCollectiveStatisticsResult(String jsonStr) {
        return init(jsonStr, TYPE_COLLECTIVE);
    }

    private static StatisticsResult init(String jsonStr, Byte type) {
        StatisticsResult statisticsResult = new StatisticsResult();
        statisticsResult.setCreateDate(new Date());
        statisticsResult.setResultContext(jsonStr);
        statisticsResult.setStatisticsType(type);
        return statisticsResult;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResultContext() {
        return resultContext;
    }

    public void setResultContext(String resultContext) {
        this.resultContext = resultContext == null ? null : resultContext.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Byte getStatisticsType() {
        return statisticsType;
    }

    public void setStatisticsType(Byte statisticsType) {
        this.statisticsType = statisticsType;
    }
}