package com.university.dao;

import com.university.model.city.City;
import com.university.model.city.CityBuilder;
import com.university.model.human.Human;
import com.university.model.human.HumanBuilder;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetConverter {
    public static City getCityFromSet(ResultSet resultSet) throws SQLException {
        if(resultSet == null) {
            throw new NullPointerException("Wrong result set value in city getter");
        }
        return new CityBuilder()
                .setId(resultSet.getLong("id"))
                .setName(resultSet.getString("name"))
                .setYear(resultSet.getLong("year"))
                .setArea(resultSet.getLong("area"))
                .setPopulation(resultSet.getLong("population"))
                .build();
    }

    public static Human getHumanFromSet(ResultSet resultSet) throws SQLException {
        if(resultSet == null) {
            throw new NullPointerException("Wrong result set value in human getter");
        }
        return new HumanBuilder()
                .setId(resultSet.getLong("id"))
                .setName(resultSet.getString("name"))
                .setAge(resultSet.getLong("age"))
                .setCity(resultSet.getString("city"))
                .setLanguage(resultSet.getString("language"))
                .build();
    }

}
