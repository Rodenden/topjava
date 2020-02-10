package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final MealService service = new MealService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        request.setCharacterEncoding("UTF-8");
        List<Meal> meals = service.getMealsList();
        CopyOnWriteArrayList<MealTo> mealTos = new CopyOnWriteArrayList<>(MealsUtil.isExcessCaloriesPerDayList(meals));
        request.setAttribute("list", mealTos);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in Post");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        log.debug("Action: " + action);

        switch (action){
            case "delete": {
                log.debug("in delete Action");
                int id = Integer.parseInt(request.getParameter("id"));
                service.deleteMealID(id);
                response.sendRedirect("meals");
            }
                break;
            case "edit": {
                log.debug("in edit Action");
                int id = Integer.parseInt(request.getParameter("id"));
                Meal meal = service.getMeal(id);
                request.setAttribute("meal", meal);
                request.setAttribute("id", id);
                doGet(request, response);
            }
                break;
            case "dataToEdit": {
                log.debug("in dataToEdit Action");
                int id = Integer.parseInt(request.getParameter("id"));
                LocalDateTime date = LocalDateTime.parse(request.getParameter("date"),DateTimeFormatter.ISO_DATE_TIME);
                String description = request.getParameter("description");
                int calories = Integer.parseInt(request.getParameter("calories"));
                Meal meal = new Meal(date, description, calories);
                service.updateMeal(id, meal);
                response.sendRedirect("meals");
            }
            break;
            case "dataToAdd": {
                log.debug("in dataToAdd Action");
                LocalDateTime date = LocalDateTime.parse(request.getParameter("date"),DateTimeFormatter.ISO_DATE_TIME);
                String description = request.getParameter("description");
                int calories = Integer.parseInt(request.getParameter("calories"));
                Meal meal = new Meal(date, description, calories);
                service.saveMeal(meal);
                response.sendRedirect("meals");
            }
            break;
        }
    }
}
