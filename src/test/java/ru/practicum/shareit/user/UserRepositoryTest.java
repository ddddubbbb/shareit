package ru.practicum.shareit.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    User user;

    @BeforeEach
    void init() {
        user = User.builder()
                .name("nameU")
                .email("nameU@mail.ru")
                .build();
    }

    @Test
    void saveUser() {
        userRepository.save(user);
        Assertions.assertEquals("nameU", userRepository.findById(user.getId()).get().getName());
    }

    @Test
    void updateUser() {
        userRepository.save(user);
        User userToUpdate = userRepository.findById(user.getId()).get();
        userToUpdate.setEmail("newmail@mail.ru");
        userRepository.save(userToUpdate);
        Assertions.assertEquals("newmail@mail.ru", userRepository.findById(userToUpdate.getId()).get().getEmail());
    }

    @AfterAll
    static void afterAll(@Autowired UserRepository userRepository) {
        userRepository.deleteAll();
    }
}