package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.concurrent.CopyOnWriteArrayList;

public interface MealsRepo {
    CopyOnWriteArrayList<Meal> getMealsList();
    void saveMeal(Meal meal);
    void deleteMealID(int id);
    void updateMeal(int id, Meal meal);
    Meal getMeal(int id);
}
