package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Long, Film> films = new HashMap<>();
    private long counter = 0L;

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        validateFilm(film);
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
        }

    @PutMapping
    public Film update(@RequestBody Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        Film film = films.get(newFilm.getId());
        if (film == null) {
            throw new ConditionsNotMetException("Фильм с id " + newFilm.getId() + " не найден");
        }

        validateFilmUpdate(newFilm);

        if (newFilm.getName() != null) {
            film.setName(newFilm.getName());
        }
        if (newFilm.getDescription() != null) {
            film.setDescription(newFilm.getDescription());
        }
        if (newFilm.getReleaseDate() != null) {
            film.setReleaseDate(newFilm.getReleaseDate());
        }
        if (newFilm.getDuration() != null) {
            film.setDuration(newFilm.getDuration());
        }

        return film;
    }

    private void validateFilm(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ConditionsNotMetException("Название не может быть пустым");
        }
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            throw new ConditionsNotMetException("Максимальная длина описания 200 символов");
        }
        LocalDate minReleaseDate = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate() == null) {
            throw new ConditionsNotMetException("Дата релиза должна быть указана");
        }
        if (film.getReleaseDate().isBefore(minReleaseDate)) {
            throw new ConditionsNotMetException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() == null) {
            throw new ConditionsNotMetException("Продолжительность должна быть указана");
        }
        if (film.getDuration() <= 0) {
            throw new ConditionsNotMetException("Продолжительность должна быть положительной");
        }
    }

    private void validateFilmUpdate(Film film) {
        if (film.getName() != null && film.getName().isBlank()) {
            throw new ConditionsNotMetException("Название не может быть пустым");
        }
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            throw new ConditionsNotMetException("Максимальная длина описания 200 символов");
        }
        if (film.getReleaseDate() != null) {
            LocalDate minReleaseDate = LocalDate.of(1895, 12, 28);
            if (film.getReleaseDate().isBefore(minReleaseDate)) {
                throw new ConditionsNotMetException("Дата релиза не может быть раньше 28 декабря 1895 года");
            }
        }
        if (film.getDuration() != null && film.getDuration() <= 0) {
            throw new ConditionsNotMetException("Продолжительность должна быть положительной");
        }
    }
    private long getNextId() {
        return ++counter;
    }
}