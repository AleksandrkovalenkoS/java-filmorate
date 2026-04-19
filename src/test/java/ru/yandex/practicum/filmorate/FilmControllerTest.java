package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private FilmController controller;

    @BeforeEach
    void setUp() {
        controller = new FilmController();
    }

    @Test
    void testNameCannotBeNull() {
        Film film = new Film();
        film.setName(null);
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(film);
        });
    }

    @Test
    void testNameCannotBeBlank() {
        Film film = new Film();
        film.setName("");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(film);
        });
    }

    @Test
    void testDescriptionCanBeLongerThan200() {
        Film film = new Film();
        film.setName("Название");
        film.setDescription("a".repeat(201));
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(film);
        });
    }

    @Test
    void testDescriptionExactly200CharsPasses() {
        Film film = new Film();
        film.setName("Название");
        film.setDescription("a".repeat(200));
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        Film created = controller.create(film);

        assertNotNull(created.getId());
        assertEquals(200, created.getDescription().length());
    }

    @Test
    void testReleaseDateCannotBeNull() {
        Film film = new Film();
        film.setName("Название");
        film.setReleaseDate(null);
        film.setDuration(120);

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(film);
        });
    }

    @Test
    void testReleaseDateCannotBeTooEarly() {
        Film film = new Film();
        film.setName("Название");
        film.setReleaseDate(LocalDate.of(1800, 1, 1));
        film.setDuration(120);

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(film);
        });
    }

    @Test
    void testReleaseDateExactlyMinDatePasses() {
        Film film = new Film();
        film.setName("Название");
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        film.setDuration(120);

        Film created = controller.create(film);

        assertNotNull(created.getId());
        assertEquals(LocalDate.of(1895, 12, 28), created.getReleaseDate());
    }

    @Test
    void testDurationMustBePositive() {
        Film film = new Film();
        film.setName("Название");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(-10);

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(film);
        });
    }

    @Test
    void testDurationCannotBeZero() {
        Film film = new Film();
        film.setName("Название");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(0);

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(film);
        });
    }

    @Test
    void testDurationCannotBeNull() {
        Film film = new Film();
        film.setName("Название");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(null);

        assertThrows(ConditionsNotMetException.class, () -> {
            controller.create(film);
        });
    }
}