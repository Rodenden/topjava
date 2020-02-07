<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<% List<MealTo> mealToList = (List<MealTo>) request.getAttribute("list");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm");%>
<table>
    <% for (MealTo meal : mealToList) {
        if (meal.isExcess()) { %>
    <tr style="color: red">
            <%} else {%>
    <tr style="color: green"><%}%>
        <td><%=formatter.format(meal.getDateTime())%>
        </td>
        <td><%=meal.getDescription()%>
        </td>
        <td><%=meal.getCalories()%>
        </td>
    </tr>
    <%}%>
</table>
</body>
</html>