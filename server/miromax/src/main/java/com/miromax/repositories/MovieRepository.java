package com.miromax.repositories;

import com.miromax.models.Movie;
import com.miromax.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m JOIN FETCH m.sessions s WHERE m.status = :movieStatus AND s.status = :sessionStatus AND DATE(s.startTime) = :sessionDate")
    List<Movie> findActiveMoviesWithSessions(Status movieStatus, Status sessionStatus, LocalDate sessionDate);
}
