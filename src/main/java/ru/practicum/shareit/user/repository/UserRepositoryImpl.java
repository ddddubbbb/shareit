package ru.practicum.shareit.user.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Qualifier("UserRepositoryImpl")
public class UserRepositoryImpl implements UserRepository {

    private Map<Long, User> users;
    private Long currentId;

    public UserRepositoryImpl() {
        currentId = 0L;
        users = new HashMap<>();
    }

    @Override
    public User create(User user) {
        user.setId(++currentId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User delete(Long userId) {
        return users.remove(userId);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(Long userId) {
        return users.get(userId);
    }

    @Override
    public boolean isExistUserInDb(Long id) {
        return users.containsKey(id);
    }

    @Override
    public Long getUserIdByEmail(String inputEmail) {
        if (inputEmail == null) {
            return null;
        }
        for (User user : users.values()) {
            String email = user.getEmail();
            if (email.equals(inputEmail)) {
                return user.getId();
            }
        }
        return null;
    }
}