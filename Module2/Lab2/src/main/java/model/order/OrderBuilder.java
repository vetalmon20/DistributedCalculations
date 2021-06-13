package app.model.order;

import app.model.drink.Drink;

import java.time.LocalDate;

public class OrderBuilder {
    private Long id;
    private Long cost;
    private String drinkName;
    private LocalDate date;
    private String userEmail;

    public OrderBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public OrderBuilder setCost(Long cost) {
        this.cost = cost;
        return this;
    }

    public OrderBuilder setDrinkName(String drinkName) {
        this.drinkName = drinkName;
        return this;
    }

    public OrderBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public OrderBuilder setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public Order build() {
        return new Order(id, cost, drinkName, date, userEmail);
    }
}
