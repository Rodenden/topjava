package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.UsersUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UsersUtil.USERS.forEach(this::save);
    }


    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        try {
            return repository.remove(id) != null;
        } catch (NullPointerException NPE) {
            return false;
        }

    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.getId() == null) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        try {
            return repository.get(id);
        } catch (NullPointerException NPE) {
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> users = (List<User>) repository.values();
        users.sort(Comparator.comparing(AbstractNamedEntity::getName));
        return users;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        List<User> users = (List<User>) repository.values();
        users = users.stream()
                .filter(u -> u.getEmail().equals(email))
                .collect(Collectors.toList());
        return users.get(0);
    }
}
