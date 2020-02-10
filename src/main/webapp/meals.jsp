<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <meta charset="UTF-8">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<% List<MealTo> mealToList = (List<MealTo>) request.getAttribute("list");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm");%>
<table>
    <tr>
        <td>ID</td>
        <td>Time</td>
        <td>Description</td>
        <td>Calories</td>
        <td></td>
        <td></td>
    </tr>
    <% for (int id = 0; id < mealToList.size(); id++) {
        MealTo meal = mealToList.get(id);
        if (meal.isExcess()) { %>
    <tr style="color: red">
            <%} else {%>
    <tr style="color: green"><%}%>
        <td><%=id%>
        </td>
        <td><%=formatter.format(meal.getDateTime())%>
        </td>
        <td><%=meal.getDescription()%>
        </td>
        <td><%=meal.getCalories()%>
        </td>
        <td>
            <form action="meals?action=edit" method="post">
                <input type="hidden" name="id" value="<%=id%>">
                <input type="submit" value="Edit">
            </form>
        </td>
        <td>
            <form action="meals?action=delete" method="post">
                <input type="hidden" name="id" value="<%=id%>">
                <input type="submit" value="Delete">
            </form>
        </td>
    </tr>
    <%}%>
</table>

<%String action = request.getParameter("action");%>

<% if (action == null || !action.equals("add")) %> <a href="meals?action=add"><button>Add new</button></a> <%;%>
<%
    if (action != null) {
        switch (action) {
            case "add": { %>
<form action="meals?action=dataToAdd" method="post">
    <table>
        <tr>
            <td><input type="datetime-local" name="date"></td>
            <td><input type="text" name="description" placeholder="Description"></td>
            <td><input type="number" name="calories" placeholder="Calories"></td>
            <td><input type="submit" value="Send"></td>
        </tr>
    </table>
</form>
<%
            }
                break;

            case "edit": {
                int id = (int) request.getAttribute("id");
                Meal meal = (Meal) request.getAttribute("meal");
%>
<form action="meals?action=dataToEdit" method="post">
    <table>
        <tr>
            <td><%=id%><input type="hidden" name="id" value="<%=id%>"></td>
            <td><input type="datetime-local" name="date" value="<%=meal.getDateTime()%>"></td>
            <td><input type="text" name="description" value="<%=meal.getDescription()%>"></td>
            <td><input type="number" name="calories" value="<%=meal.getCalories()%>"></td>
            <td><input type="submit" value="Send"></td>
        </tr>
    </table>
</form>
<%
            }
                break;
        }
    }
%>
</body>
</html>