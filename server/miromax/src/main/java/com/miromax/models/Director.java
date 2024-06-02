package com.miromax.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "directors")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "directors_seq")
    @SequenceGenerator(name = "directors_seq", sequenceName = "directors_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
//    @Column(name = "portrait_url", nullable = false)
//    private String portraitUrl;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @ManyToMany
    @JoinTable(
            name = "movie_directors",
            inverseJoinColumns = @JoinColumn(name = "movie_id", nullable = false),
            joinColumns = @JoinColumn(name = "director_id", nullable = false)
    )
    private List<Movie> movies;
}