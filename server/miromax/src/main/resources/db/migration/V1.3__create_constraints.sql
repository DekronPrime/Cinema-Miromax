ALTER TABLE IF EXISTS actors
    ADD CONSTRAINT actors_countries_fk FOREIGN KEY (country_id) REFERENCES countries (id);

ALTER TABLE IF EXISTS bookings
    ADD CONSTRAINT bookings_sessions_fk FOREIGN KEY (session_id) REFERENCES sessions (id),
    ADD CONSTRAINT bookings_users_fk FOREIGN KEY (user_id) REFERENCES users (id),
    ADD CONSTRAINT bookings_rows_fk FOREIGN KEY (row_id) REFERENCES rows (id);

ALTER TABLE IF EXISTS cities
    ADD CONSTRAINT cities_countries_fk FOREIGN KEY (country_id) REFERENCES countries (id);

ALTER TABLE IF EXISTS comments
    ADD CONSTRAINT comments_users_fk FOREIGN KEY (user_id) REFERENCES users (id),
    ADD CONSTRAINT comments_movies_fk FOREIGN KEY (movie_id) REFERENCES movies (id);

ALTER TABLE IF EXISTS directors
    ADD CONSTRAINT directors_countries_fk FOREIGN KEY (country_id) REFERENCES countries (id);

ALTER TABLE IF EXISTS halls
    ADD CONSTRAINT halls_locations_fk FOREIGN KEY (location_id) REFERENCES locations (id);

ALTER TABLE IF EXISTS locations
    ADD CONSTRAINT locations_cities_fk FOREIGN KEY (city_id) REFERENCES cities (id);

ALTER TABLE IF EXISTS movies
    ADD CONSTRAINT movies_countries_fk FOREIGN KEY (country_id) REFERENCES countries (id);

ALTER TABLE IF EXISTS rows
    ADD CONSTRAINT rows_halls_fk FOREIGN KEY (hall_id) REFERENCES halls (id);

ALTER TABLE IF EXISTS sessions
    ADD CONSTRAINT sessions_movies_fk FOREIGN KEY (movie_id) REFERENCES movies (id),
    ADD CONSTRAINT sessions_halls_fk FOREIGN KEY (hall_id) REFERENCES halls (id);

ALTER TABLE IF EXISTS movie_genres
    ADD CONSTRAINT movies_genres_movie_fk FOREIGN KEY (movie_id) REFERENCES movies (id),
    ADD CONSTRAINT movies_genres_genre_fk FOREIGN KEY (genre_id) REFERENCES genres (id);

-- ALTER TABLE IF EXISTS movie_countries
--     ADD CONSTRAINT movie_countries_movie_fk FOREIGN KEY (movie_id) REFERENCES movies (id),
--     ADD CONSTRAINT movie_countries_country_fk FOREIGN KEY (country_id) REFERENCES countries (id);

ALTER TABLE IF EXISTS movie_actors
    ADD CONSTRAINT movies_actors_movie_fk FOREIGN KEY (movie_id) REFERENCES movies (id),
    ADD CONSTRAINT movies_actors_actor_fk FOREIGN KEY (actor_id) REFERENCES actors (id);

ALTER TABLE IF EXISTS movie_directors
    ADD CONSTRAINT movies_directors_movie_fk FOREIGN KEY (movie_id) REFERENCES movies (id),
    ADD CONSTRAINT movies_directors_director_fk FOREIGN KEY (director_id) REFERENCES directors (id);
