package app.model.drink;

import java.util.List;

public class DrinkBuilder {
    private Long id;
    private String name;
    private List<Long> ingredientIds;
    private Long cost;

    public DrinkBuilder setId(Long id) {
        this.id = id;
        return this;
    }
    public DrinkBuilder setName(String name) {
        this.name = name;
        return this;
    }
    public DrinkBuilder setIngredientIds(List<Long> ingredientIds) {
        this.ingredientIds = ingredientIds;
        return this;
    }

    public DrinkBuilder setCost(Long cost) {
        this.cost = cost;
        return this;
    }

    public Drink build() {
        return new Drink(id, name, ingredientIds, cost);
    }
}
