package com.miromax.models;

import com.miromax.models.enums.ActorPopularity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "actors")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actors_seq")
    @SequenceGenerator(name = "actors_seq", sequenceName = "actors_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
//    @Column(name = "portrait_url", nullable = false)
//    private String portraitUrl;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "popularity", nullable = false)
    private ActorPopularity popularity;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @ManyToMany
    @JoinTable(
            name = "movie_actors",
            inverseJoinColumns = @JoinColumn(name = "movie_id", nullable = false),
            joinColumns = @JoinColumn(name = "actor_id", nullable = false)
    )
    private List<Movie> movies;
}
