package app.model.ingredient;

public class IngredientBuilder {
    private Long id;
    private String name;
    private Long cost;
    private Long amount;

    public IngredientBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public IngredientBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public IngredientBuilder setCost(Long cost) {
        this.cost = cost;
        return this;
    }

    public IngredientBuilder setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public Ingredient build() {
        return new Ingredient(id, name, cost, amount);
    }
}
