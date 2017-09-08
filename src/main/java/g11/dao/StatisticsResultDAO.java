package g11.dao;

import g11.model.StatisticsResult;

public interface StatisticsResultDAO {

    int insert(StatisticsResult record);

    StatisticsResult selectLast();

    StatisticsResult selectLastPerson();
}