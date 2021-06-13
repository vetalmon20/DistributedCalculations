package com.university.dao;

import com.university.model.city.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CityDaoTask10 {
    private final static Logger logger = LogManager.getLogger(CityDaoTask10.class);
    private final Connection connection;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("sql");

    public CityDaoTask10(Connection connection) {
        this.connection = connection;
    }

    public City getCityByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("city.findByName"));
            preparedStatement.setString(1, name);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                return ResultSetConverter.getCityFromSet(set);
            }
            logger.info("City has been successfully found by name");
        } catch (SQLException ex) {
            logger.warn("Could not find city with name{}: {}", name, ex.getMessage());
        }
        return null;
    }

    public City getCityByPopulation(Long population) {
        long bottom = population - 100000L;
        long top = population + 100000L;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("city.findByPopulation"));
            preparedStatement.setLong(1, bottom);
            preparedStatement.setLong(2, top);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                return ResultSetConverter.getCityFromSet(set);
            }
            logger.info("City has been successfully found by population");
        } catch (SQLException ex) {
            logger.warn("Could not find city with population{}: {}", population, ex.getMessage());
        }
        return null;
    }
}
