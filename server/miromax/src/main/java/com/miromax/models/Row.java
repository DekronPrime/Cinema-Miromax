package com.miromax.models;

import com.miromax.models.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rows")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Row {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rows_seq")
    @SequenceGenerator(name = "rows_seq", sequenceName = "rows_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @Column(name = "number", nullable = false)
    private Integer number;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatType type;
    @Column(name = "capacity", nullable = false)
    private Integer capacity;
}