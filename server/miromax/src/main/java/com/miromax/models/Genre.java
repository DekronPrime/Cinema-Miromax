package com.miromax.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "genres")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genres_seq")
    @SequenceGenerator(name = "genres_seq", sequenceName = "genres_seq", allocationSize = 1)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "movie_genres",
            inverseJoinColumns = @JoinColumn(name = "movie_id", nullable = false),
            joinColumns = @JoinColumn(name = "genre_id", nullable = false)
    )
    private List<Movie> movies;
}