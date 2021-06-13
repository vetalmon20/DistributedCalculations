package app.model.ingredient;

public class Ingredient {
    private Long id;
    private String name;
    private Long cost;
    private Long amount;

    public Ingredient(Long id, String name, Long cost, Long amount) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
