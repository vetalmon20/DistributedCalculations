package app.dao;

import app.model.ingredient.Ingredient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class IngredientsDao {
    private final static Logger logger = LogManager.getLogger(IngredientsDao.class);
    private final Connection connection;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("sql");

    public IngredientsDao(Connection connection) {
        this.connection = connection;
    }

        public List<Ingredient> getAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        ResultSet resultSet;

        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("ingredient.findAll"));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ingredient ingredient = ResultSetConverter.getIngredientFromResultSet(resultSet);

                ingredients.add(ingredient);
            }
            logger.info("Ingredients has been successfully received");
        } catch (SQLException ex) {
            logger.warn("Ingredients are not reachable: {}", ex.getMessage());
        }
        return ingredients;
    }

    public void changeIngredientAmount(Long id, Long newAmount) {
        try {
            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("ingredient.changeAmount"));
            statement.setLong(1, newAmount);
            statement.setLong(2, id);
            statement.executeUpdate();
            logger.info("Ingredient amount has been successfully changed");
        } catch (SQLException ex) {
            logger.warn("Cannot change ingredient's amount: {}", ex.getMessage());
        }
    }

    public List<String> getAllByIds(List<Long> ids) {
        List<String> names = new ArrayList<>();
        ResultSet resultSet;

        try {
            for (Long id : ids) {
                PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("ingredient.findNameById"));
                statement.setLong(1, id);
                resultSet = statement.executeQuery();
                resultSet.next();
                String currName = resultSet.getString("name");
                names.add(currName);
            }

            logger.info("Ingredients has been successfully received by their ids");
        } catch (SQLException ex) {
            logger.warn("Ingredients are not reachable by ids: {}", ex.getMessage());
        }

        return names;
    }

    public Ingredient findById(Long id) {
        Ingredient ingredient = null;
        ResultSet resultSet;

        try {

            PreparedStatement statement = connection.prepareStatement(resourceBundle.getString("ingredient.findById"));
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();
            ingredient = ResultSetConverter.getIngredientFromResultSet(resultSet);

            logger.info("Ingredients has been successfully received by their ids");
        } catch (SQLException ex) {
            logger.warn("Ingredients are not reachable by ids: {}", ex.getMessage());
        }

        return ingredient;
    }
}
