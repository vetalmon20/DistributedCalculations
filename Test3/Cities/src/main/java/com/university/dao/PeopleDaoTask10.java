package com.university.dao;

import com.university.model.human.Human;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class PeopleDaoTask10 {
    private final static Logger logger = LogManager.getLogger(PeopleDaoTask10.class);
    private final Connection connection;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("sql");

    public PeopleDaoTask10(Connection connection) {
        this.connection = connection;
    }

    public List<Human> getPeopleByParameters(String city, String language) {
        List<Human> people = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("human.findByParams"));
            preparedStatement.setString(1, city);
            preparedStatement.setString(2, language);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                people.add(ResultSetConverter.getHumanFromSet(set));
            }

            logger.info("People has been successfully found by params");
            return people;
        } catch (SQLException ex) {
            logger.warn("Could not find people with city and language {}, {}: {}", city, language, ex.getMessage());
        }
        return null;
    }

    public List<Human> getPeopleByLanguage(String language) {
        List<Human> people = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("human.findByLanguage"));
            preparedStatement.setString(1, language);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                people.add(ResultSetConverter.getHumanFromSet(set));
            }

            logger.info("People has been successfully found by language");
            return people;
        } catch (SQLException ex) {
            logger.warn("Could not find people with language {}: {}", language, ex.getMessage());
        }
        return null;
    }

    public List<Human> getPeopleByCity(String city) {
        List<Human> people = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("human.findByCity"));
            preparedStatement.setString(1, city);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                people.add(ResultSetConverter.getHumanFromSet(set));
            }
            logger.info("People has been successfully found by city");
            return people;
        } catch (SQLException ex) {
            logger.warn("Could not find people with city{}: {}", city, ex.getMessage());
        }
        return null;
    }

    public List<Human> getOldestPeople() {
        List<Human> people = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("human.findOldest"));
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                people.add(ResultSetConverter.getHumanFromSet(set));
            }
            logger.info("Oldest people has successfully be found");
            return people;
        } catch (SQLException ex) {
            logger.warn("Could not find the oldest people: {}",  ex.getMessage());
        }
        return null;

    }
}
