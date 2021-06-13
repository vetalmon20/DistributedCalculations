package app.model.drink;

import java.util.List;

public class Drink {
    private Long id;
    private String name;
    private List<Long> ingredientIds;
    private List<String> ingredientNames;
    private Long cost;

    public Drink(Long id, String name, List<Long> ingredientIds, Long cost) {
        this.id = id;
        this.name = name;
        this.ingredientIds = ingredientIds;
        this.cost = cost;
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

    public List<Long> getIngredientIds() {
        return ingredientIds;
    }

    public void setIngredientIds(List<Long> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }

    public List<String> getIngredientNames() {
        return ingredientNames;
    }

    public void setIngredientNames(List<String> ingredientNames) {
        this.ingredientNames = ingredientNames;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }


}
