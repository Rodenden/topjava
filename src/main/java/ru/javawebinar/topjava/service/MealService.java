package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealsRepo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.MealServlet;

import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealService implements MealsRepo {
    private static CopyOnWriteArrayList<Meal> meals = new CopyOnWriteArrayList<>(MealsUtil.meals);

    @Override
    public CopyOnWriteArrayList<Meal> getMealsList() {
        meals.sort(Comparator.comparing(Meal::getDateTime));
        return meals;
    }

    @Override
    public void updateMeal(int id, Meal meal) {
        meals.set(id, meal);
    }

    @Override
    public Meal getMeal(int id) {
        return meals.get(id);
    }

    @Override
    public void saveMeal(Meal meal) {
        meals.add(meal);
    }

    @Override
    public void deleteMealID(int id) {
        meals.remove(id);
    }
}
