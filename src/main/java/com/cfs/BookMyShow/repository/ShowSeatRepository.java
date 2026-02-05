package com.cfs.BookMyShow.repository;

import com.cfs.BookMyShow.model.Show;
import com.cfs.BookMyShow.model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {

    List<ShowSeat> findByShowId(Long movieId);

    List<ShowSeat> findByShow_IdAndStatus(Long showId, String status);

}
