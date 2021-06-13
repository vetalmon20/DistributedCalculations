package app.dao;

public class DaoFactory {
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static UsersDao usersDao = null;
    private static IngredientsDao ingredientsDao = null;
    private static DrinksDao drinksDao = null;
    private static OrdersDao ordersDao = null;

    public static UsersDao createUserDao() {
        if(usersDao == null) {
            usersDao = new UsersDao(connectionPool.getConnection());
        }
        return usersDao;
    }

    public static IngredientsDao createIngredientsDao() {
        if(ingredientsDao == null) {
            ingredientsDao = new IngredientsDao(connectionPool.getConnection());
        }
        return ingredientsDao;
    }

    public static DrinksDao createDrinksDao() {
        if(drinksDao == null) {
            drinksDao = new DrinksDao(connectionPool.getConnection());
        }
        return drinksDao;
    }

    public static OrdersDao createOrdersDao() {
        if(ordersDao == null) {
            ordersDao = new OrdersDao(connectionPool.getConnection());
        }
        return ordersDao;
    }
}
