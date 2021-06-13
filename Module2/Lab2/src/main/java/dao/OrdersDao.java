package app.dao;

import app.model.drink.Drink;
import app.model.order.Order;
import app.model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrdersDao {
    private final static Logger logger = LogManager.getLogger(OrdersDao.class);
    private final Connection connection;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("sql");

    public OrdersDao(Connection connection) {
        this.connection = connection;
    }

    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        ResultSet resultSet;

        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("orders.findAll"));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = ResultSetConverter.getOrderFromResultSet(resultSet);
                orders.add(order);
            }

            logger.info("Orders has been successfully received");
        } catch (SQLException ex) {
            logger.warn("Orders are not reachable: {}", ex.getMessage());
        }
        return orders;
    }

    public List<Order> getAllByEmail(String email) {
        List<Order> orders = new ArrayList<>();
        ResultSet resultSet;

        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("orders.findByEmail"));
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = ResultSetConverter.getOrderFromResultSet(resultSet);
                orders.add(order);
            }


            logger.info("Orders has been successfully received by email");
        } catch (SQLException ex) {
            logger.warn("Orders are not reachable by email: {}", ex.getMessage());
        }

        return orders;
    }

    public Optional<Order> create(Order entity) {
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement(resourceBundle.getString("orders.create"), Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, entity.getCost());
            preparedStatement.setDate(2, Date.valueOf(entity.getDate()));
            preparedStatement.setString(3, entity.getUserEmail());
            preparedStatement.setString(4, entity.getDrinkName());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }

            logger.info("Order has been successfully created");
            return Optional.of(entity);
        } catch (SQLException ex) {
            logger.warn("Error with adding an order: {}", ex.getMessage());
        }

        return Optional.empty();
    }
}
