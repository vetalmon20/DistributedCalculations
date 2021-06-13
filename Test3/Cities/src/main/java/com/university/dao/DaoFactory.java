package com.university.dao;

import com.university.connection.ConnectionPool;

public class DaoFactory {
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static CityDaoTask10 cityDaoTask10 = null;
    private static PeopleDaoTask10 peopleDaoTask10 = null;

    public static CityDaoTask10 createCityDao() {
        if(cityDaoTask10 == null) {
            cityDaoTask10 = new CityDaoTask10(connectionPool.getConnection());
        }
        return cityDaoTask10;
    }

    public static PeopleDaoTask10 createPeopleDao() {
        if(peopleDaoTask10 == null) {
            peopleDaoTask10 = new PeopleDaoTask10(connectionPool.getConnection());
        }
        return peopleDaoTask10;
    }
}
