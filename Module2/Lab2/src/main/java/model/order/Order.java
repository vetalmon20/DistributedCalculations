package app.model.order;

import java.time.LocalDate;

public class Order {
    private Long id;
    private Long cost;
    private String drinkName;
    private LocalDate date;
    private String userEmail;

    public Order(Long id, Long cost, String drinkName, LocalDate date, String userEmail) {
        this.id = id;
        this.cost = cost;
        this.drinkName = drinkName;
        this.date = date;
        this.userEmail = userEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkId) {
        this.drinkName = drinkId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
