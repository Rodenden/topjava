package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        Meal mealFromRep = repository.get(meal.getId());
        if (mealFromRep.getUserId() == userId){
            repository.put(meal.getId(), meal);
            return meal;
        }
        else return null;
        // handle case: update, but not present in storage
        //return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal;
        try {
            meal = repository.get(id);
        } catch (NullPointerException NPE) {
            return false;
        }
        if (meal.getUserId() == userId) {
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal;
        try {
            meal = repository.get(id);
        } catch (NullPointerException NPE) {
            return null;
        }
        return meal.getUserId() == userId ? meal : null;
    }

    @Override
    public Collection<Meal> getAll(LocalDateTime min, LocalDateTime max, int userId) {
        final LocalDateTime MIN =
                min == null ? LocalDateTime.MIN : min;

        final LocalDateTime MAX =
                max == null ? LocalDateTime.MAX : max;

        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> meal.getDateTime().compareTo(MIN) >= 0)
                .filter(meal -> meal.getDateTime().compareTo(MAX) <= 0)
                .sorted((meal1, meal2) -> meal2.getDateTime().compareTo(meal1.getDateTime()))
                .collect(Collectors.toList());
    }
}

