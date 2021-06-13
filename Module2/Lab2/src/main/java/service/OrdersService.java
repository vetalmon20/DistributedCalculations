package app.service;

import app.dao.DaoFactory;
import app.dao.DrinksDao;
import app.dao.OrdersDao;
import app.model.drink.Drink;
import app.model.order.Order;

import java.util.List;
import java.util.Optional;

public class OrdersService {
    private final OrdersDao ordersDao;

    public OrdersService() {
        this.ordersDao = DaoFactory.createOrdersDao();
    }

    public Order addOrder(Order entity) throws Exception {
        Optional<Order> out = ordersDao.create(entity);
        if(!out.isPresent()) {
            throw new Exception("Can't create such an order. OrderService");
        }
        return out.get();
    }

    public List<Order> getUserOrders(String email) {
        return ordersDao.getAllByEmail(email);
    }
}
