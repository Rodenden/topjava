package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User("Vasya", "Vasya@ydx.com", "qwertyVasya", Role.ROLE_USER),
            new User("Petya", "Petya@ydx.com", "qwertyPetya", Role.ROLE_ADMIN),
            new User("Dima", "Dima@ydx.com", "qwertyDima", Role.ROLE_USER, Role.ROLE_ADMIN),
            new User("Sasha", "Sasha@ydx.com", "qwertySasha", Role.ROLE_USER),
            new User("Vanya", "Vanya@ydx.com", "qwertyVanya", Role.ROLE_ADMIN),
            new User("Evgeniy", "Evgeniy@ydx.com", "qwertyEvgeniy", Role.ROLE_USER, Role.ROLE_ADMIN),
            new User("Vasya", "Vasya12345@gmail.com", "opossum777", Role.ROLE_USER));
}
