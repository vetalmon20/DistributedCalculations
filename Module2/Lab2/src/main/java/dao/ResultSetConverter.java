package app.dao;

import app.model.drink.Drink;
import app.model.drink.DrinkBuilder;
import app.model.ingredient.Ingredient;
import app.model.ingredient.IngredientBuilder;
import app.model.order.Order;
import app.model.order.OrderBuilder;
import app.model.user.User;
import app.model.user.UserBuilder;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ResultSetConverter {

    public static User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserBuilder()
                .setId(resultSet.getLong("id"))
                .setMoney(resultSet.getLong("money"))
                .setEmail(resultSet.getString("email"))
                .setName(resultSet.getString("name"))
                .setPass(resultSet.getString("password"))
                .setIsAdmin(resultSet.getString("role"))
                .build();
    }

    public static Ingredient getIngredientFromResultSet(ResultSet resultSet) throws SQLException {
        return new IngredientBuilder()
                .setAmount(resultSet.getLong("amount"))
                .setCost(resultSet.getLong("cost"))
                .setId(resultSet.getLong("id"))
                .setName(resultSet.getString("name"))
                .build();
    }

    public static Drink getDrinkFromResultSet(ResultSet resultSet) throws SQLException {
        Array ingrIdsSqlArr = resultSet.getArray("main_ingredients_id");
        Long[] ingrIdsJavaArr = (Long[])ingrIdsSqlArr.getArray();
        List<Long> ingrIds = Arrays.asList(ingrIdsJavaArr);

         return new DrinkBuilder()
                 .setCost(resultSet.getLong("cost"))
                 .setId(resultSet.getLong("id"))
                 .setName(resultSet.getString("name"))
                 .setIngredientIds(ingrIds)
                 .build();
    }

    public static Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        return new OrderBuilder()
                .setUserEmail(resultSet.getString("user_email"))
                .setDrinkName(resultSet.getString("drink_name"))
                .setDate(resultSet.getDate("date").toLocalDate())
                .setId(resultSet.getLong("id"))
                .setCost(resultSet.getLong("cost"))
                .build();
    }
}
