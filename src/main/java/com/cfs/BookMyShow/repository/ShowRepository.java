package com.cfs.BookMyShow.repository;

import com.cfs.BookMyShow.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show,Long> {
    List<Show> findByMovieId(Long moiveId);
    List<Show> findByScreenId(Long screenId);

    List<Show> findByStartTimeBetween(LocalDateTime start,LocalDateTime end);

    List<Show> findByMovie_IdAndScreen_Theater_city(Long moiveId,String city);

}
