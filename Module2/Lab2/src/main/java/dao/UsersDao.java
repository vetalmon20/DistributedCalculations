package app.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import app.model.user.Roles;
import app.model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UsersDao {
    private final static Logger logger = LogManager.getLogger(UsersDao.class);
    private final Connection connection;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("sql");

    public UsersDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<User> create(User entity) {
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement(resourceBundle.getString("user.create"), Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, entity.getPass());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getName());
            preparedStatement.setString(4, Roles.USER.name());
            preparedStatement.setBigDecimal(5, new BigDecimal(100));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(5));
            }

            logger.info("User has been successfully created");
            return Optional.of(entity);
        } catch (SQLException ex) {
            logger.warn("Error with adding a user: {}", ex.getMessage());
        }

        return Optional.empty();
    }

    public void changeMoney(String email, Long money) {
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("user.changeMoney"));
            statement.setLong(1, money);
            statement.setString(2, email);
            statement.executeUpdate();
            logger.info("Money has been successfully changed");
        } catch (SQLException ex) {
            logger.warn("Error with changing the money value of user:" + email + ". Error: {}", ex.getMessage());
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("user.findByEmail"));
            preparedStatement.setString(1, email);
            ResultSet set = preparedStatement.executeQuery();
            if (set.next()) {
                return Optional.of(ResultSetConverter.getUserFromResultSet(set));
            }
            logger.info("User has been successfully found by email");
        } catch (SQLException ex) {
            logger.warn("Could not find user with email address {}: {}", email, ex.getMessage());
        }
        return Optional.empty();
    }


}
