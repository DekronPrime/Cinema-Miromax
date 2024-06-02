package com.miromax.models;

import com.miromax.models.enums.CommentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq")
    @SequenceGenerator(name = "comments_seq", sequenceName = "comments_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "written_at", nullable = false)
    private LocalDateTime writtenAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    @Column(name = "likes", nullable = false)
    private Integer likes;
    @Column(name = "dislikes", nullable = false)
    private Integer dislikes;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommentStatus status;
}