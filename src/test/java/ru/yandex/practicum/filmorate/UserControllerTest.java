package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private UserController controller;

    @BeforeEach
    void setUp() {
        controller = new UserController();
    }

    @Test
    void testEmailCannotBeNull() {
        User user = new User();
        user.setEmail(null);
        user.setLogin("testuser");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(user);
        });
    }

    @Test
    void testEmailCannotBeBlank() {
        User user = new User();
        user.setEmail("");
        user.setLogin("testuser");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(user);
        });
    }

    @Test
    void testEmailMustContainAtSymbol() {
        User user = new User();
        user.setEmail("test.mail.com");
        user.setLogin("testuser");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(user);
        });
    }

    @Test
    void testLoginCannotBeNull() {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setLogin(null);
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(user);
        });
    }

    @Test
    void testLoginCannotBeBlank() {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setLogin("");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(user);
        });
    }

    @Test
    void testLoginCannotContainSpaces() {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setLogin("test user");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(user);
        });
    }

    @Test
    void testBirthdayCannotBeNull() {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setLogin("testuser");
        user.setBirthday(null);

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(user);
        });
    }

    @Test
    void testBirthdayCannotBeInFuture() {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setLogin("testuser");
        user.setBirthday(LocalDate.now().plusDays(1));

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(user);
        });
    }

    @Test
    void testNameIsReplacedByLoginWhenBlank() {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setLogin("testuser");
        user.setName("");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        User created = controller.create(user);

        assertEquals("testuser", created.getName());
    }
}