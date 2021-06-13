package app.service;

import app.dao.DaoFactory;
import app.dao.DrinksDao;
import app.dao.IngredientsDao;
import app.model.drink.Drink;
import app.model.ingredient.Ingredient;

import java.util.List;

public class IngredientsService {
    private final IngredientsDao ingredientsDao;

    public IngredientsService() {
        this.ingredientsDao = DaoFactory.createIngredientsDao();
    }

    public List<String> getIngredientNames(List<Long> ids) throws Exception {
        List<String> names = ingredientsDao.getAllByIds(ids);
        if(names == null || names.size() <= 0) {
            throw new Exception("Can't find such ingredients by their ids");
        }
        return names;
    }

    public List<Ingredient> getIngredients() throws Exception {
        List<Ingredient> ingredients = ingredientsDao.getAll();
        if(ingredients == null || ingredients.size() <= 0) {
            throw new Exception("Can't find ingredients at all");
        }
        return ingredients;
    }

    public Ingredient getIngredient(Long id) throws Exception {
        Ingredient ingredient = ingredientsDao.findById(id);
        if(ingredient == null) {
            throw new Exception("Can't find ingredient by curr id");
        }
        return ingredient;
    }

    public void setIngredientAmount(Long id, Long amount) {
        ingredientsDao.changeIngredientAmount(id, amount);
    }
}
