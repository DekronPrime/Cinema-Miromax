package com.miromax.models;

import com.miromax.models.enums.AgeLimit;
import com.miromax.models.enums.DubLanguage;
import com.miromax.models.enums.Format;
import com.miromax.models.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movies_seq")
    @SequenceGenerator(name = "movies_seq", sequenceName = "movies_seq", allocationSize = 1)
    private Long id;
    @Column(name = "title_ukr", nullable = false)
    private String titleUkr;
    @Column(name = "title_original", nullable = false)
    private String titleOriginal;
    @Column(name = "poster_url", nullable = false)
    private String posterUrl;
    @Column(name = "year", nullable = false)
    private Integer year;

    @ManyToMany(mappedBy = "movies", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Genre> genres;

    @Column(name = "dub_language", nullable = false)
    @Enumerated(EnumType.STRING)
    private DubLanguage dubLanguage;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @ManyToMany(mappedBy = "movies", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<Director> directors;

    @Column(name = "age_limit", nullable = false)
    @Enumerated(EnumType.STRING)
    private AgeLimit ageLimit;
    @Column(name = "duration", nullable = false)
    private Integer duration;
    @Column(name = "imdb_rating", nullable = false)
    private Double imdbRating;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "trailer_url", nullable = false)
    private String trailerUrl;
    @Column(name = "format", nullable = false)
    @Enumerated(EnumType.STRING)
    private Format format;
    @Column(name = "likes", nullable = false)
    private Integer likes;
    @Column(name = "dislikes", nullable = false)
    private Integer dislikes;

    @ManyToMany(mappedBy = "movies", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Actor> actors;

    @Column(name = "start_rental_date", nullable = false)
    private LocalDate startRentalDate;
    @Column(name = "final_rental_date", nullable = false)
    private LocalDate finalRentalDate;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Session> sessions;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public void addGenres(List<Genre> genres) {
        setGenres(genres);
        for (Genre genre : genres) {
            genre.getMovies().add(this);
        }
    }

    public void removeGenres(List<Genre> genres) {
        setGenres(Collections.emptyList());
        for (Genre genre : genres) {
            genre.getMovies().remove(this);
        }
    }

    public void addActors(List<Actor> actors) {
        setActors(actors);
        for (Actor actor : actors) {
            actor.getMovies().add(this);
        }
    }

    public void removeActors(List<Actor> actors) {
        setActors(Collections.emptyList());
        for (Actor actor : actors) {
            actor.getMovies().remove(this);
        }
    }

    public void addDirectors(List<Director> directors) {
        setDirectors(directors);
        for (Director director : directors) {
            director.getMovies().add(this);
        }
    }

    public void removeDirectors(List<Director> directors) {
        setDirectors(Collections.emptyList());
        for (Director director : directors) {
            director.getMovies().remove(this);
        }
    }
}