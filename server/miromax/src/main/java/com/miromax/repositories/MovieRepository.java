package com.miromax.repositories;

import com.miromax.models.Movie;
import com.miromax.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
        @Query("SELECT m FROM Movie m JOIN FETCH m.sessions s WHERE m.status = :movieStatus AND s.status = :sessionStatus AND DATE(s.startTime) = :sessionDate AND s.hall.location.id = :locationId")
        List<Movie> findActiveMoviesWithSessions(Status movieStatus, Status sessionStatus, LocalDate sessionDate, Long locationId);

        @Query("SELECT m FROM Movie m WHERE m.status = :status AND m.startRentalDate > :currentDate")
        List<Movie> findUpcomingMovies(Status status, LocalDate currentDate);

        @Query("SELECT m FROM Movie m JOIN m.directors d WHERE " +
                "LOWER(m.titleOriginal) LIKE LOWER(CONCAT('%', :value, '%')) OR " +
                "LOWER(m.titleUkr) LIKE LOWER(CONCAT('%', :value, '%')) OR " +
                "LOWER(d.firstName) LIKE LOWER(CONCAT('%', :value, '%')) OR " +
                "LOWER(d.middleName) LIKE LOWER(CONCAT('%', :value, '%')) OR " +
                "LOWER(d.lastName) LIKE LOWER(CONCAT('%', :value, '%'))")
        List<Movie> searchInMovies(@Param("value") String value);
}
