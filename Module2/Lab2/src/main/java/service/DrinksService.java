package app.service;

import app.dao.DaoFactory;
import app.dao.DrinksDao;
import app.model.drink.Drink;

import java.util.List;

public class DrinksService {
    private final DrinksDao drinksDao;

    public DrinksService() {
        this.drinksDao = DaoFactory.createDrinksDao();
    }

    public List<Drink> getAllDrinks() {
        return drinksDao.getAll();
    }

    public Drink getDrinkById(Long id) throws Exception {
        Drink out = drinksDao.findById(id);
        if(out == null) {
            throw new Exception("Can't find a drink with current id");
        }
        return out;
    }
}
