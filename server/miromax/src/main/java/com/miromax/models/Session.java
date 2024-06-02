package com.miromax.models;

import com.miromax.models.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sessions")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sessions_seq")
    @SequenceGenerator(name = "sessions_seq", sequenceName = "sessions_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "prices", columnDefinition = "integer[]", nullable = false)
    private int[] prices;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
