package com.miromax.models;

import com.miromax.models.enums.HallType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "halls")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "halls_seq")
    @SequenceGenerator(name = "halls_seq", sequenceName = "halls_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private HallType type;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private List<Row> rows;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    private List<Session> sessions;
}