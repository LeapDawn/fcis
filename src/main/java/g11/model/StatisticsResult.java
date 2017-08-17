package g11.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.List;

public class StatisticsResult {

    private final static Byte TYPE_PERSON = 0;
    private final static Byte TYPE_COLLECTIVE = 1;

    private Integer id;

    private String resultContext;

    private Date createDate;

    private Byte statisticsType;

    public static StatisticsResult getPersonStatisticsResult(List list) throws JsonProcessingException {
        StatisticsResult statisticsResult = new StatisticsResult();
        statisticsResult.setResultContext(new ObjectMapper().writeValueAsString(list));
        statisticsResult.setCreateDate(new Date());
        statisticsResult.setStatisticsType(TYPE_PERSON);
        return statisticsResult;
    }

    public static StatisticsResult getPersonStatisticsResult(String jsonStr) {
        StatisticsResult statisticsResult = new StatisticsResult();
        statisticsResult.setResultContext(jsonStr);
        statisticsResult.setCreateDate(new Date());
        statisticsResult.setStatisticsType(TYPE_PERSON);
        return statisticsResult;
    }

    public static StatisticsResult getCollectiveStatisticsResult(List list) throws JsonProcessingException {
        StatisticsResult statisticsResult = new StatisticsResult();
        statisticsResult.setResultContext(new ObjectMapper().writeValueAsString(list));
        statisticsResult.setCreateDate(new Date());
        statisticsResult.setStatisticsType(TYPE_COLLECTIVE);
        return statisticsResult;
    }

    public static StatisticsResult getCollectiveStatisticsResult(String jsonStr) {
        StatisticsResult statisticsResult = new StatisticsResult();
        statisticsResult.setResultContext(jsonStr);
        statisticsResult.setCreateDate(new Date());
        statisticsResult.setStatisticsType(TYPE_COLLECTIVE);
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