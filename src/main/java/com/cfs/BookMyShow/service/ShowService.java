package com.cfs.BookMyShow.service;

import com.cfs.BookMyShow.dto.*;
import com.cfs.BookMyShow.exception.ResourceNotFoundException;
import com.cfs.BookMyShow.model.Movie;
import com.cfs.BookMyShow.model.Screen;
import com.cfs.BookMyShow.model.Show;
import com.cfs.BookMyShow.model.ShowSeat;
import com.cfs.BookMyShow.repository.MovieRepository;
import com.cfs.BookMyShow.repository.ScreenRepository;
import com.cfs.BookMyShow.repository.ShowRepository;
import com.cfs.BookMyShow.repository.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    private ShowDto createShow(ShowDto showDto){
        Show show = new Show();
        Movie movie = movieRepository.findById(showDto.getMoive().getId())
                .orElseThrow(()-> new ResourceNotFoundException("movie not found"));

        Screen screen = screenRepository.findById(showDto.getScreen().getId())
                .orElseThrow(() -> new ResourceNotFoundException("screen not found"));

        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(showDto.getStartTime());
        show.setEndTime(showDto.getEndTime());

        Show savedShow =showRepository.save(show);

        List<ShowSeat> availableSeats = showSeatRepository.findByShow_IdAndStatus(savedShow.getId(),"AVAILABLE");

        return mapToDto(savedShow,availableSeats);
    }

    public ShowDto getShowById(Long id){
        Show show = showRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Show not found"));
        List<ShowSeat> availableSeats = showSeatRepository.findByShow_IdAndStatus(show.getId(),"AVAILABLE");
        return mapToDto(show,availableSeats);
    }

    public List<ShowDto> getAllShows(){
        List<Show> shows = showRepository.findAll();
        return  shows.stream()
                .map(show->{
                    List<ShowSeat> availableSeats= showSeatRepository.findByShow_IdAndStatus(show.getId(),"AVAILABLE");
                    return mapToDto(show,availableSeats);
                })
                .collect(Collectors.toList());
    }

    public List<ShowDto> getShowByMovie(Long moiveId){
        List<Show> shows = showRepository.findByMovieId(moiveId);
        return  shows.stream()
                .map(show->{
                    List<ShowSeat> availableSeats= showSeatRepository.findByShow_IdAndStatus(show.getId(),"AVAILABLE");
                    return mapToDto(show,availableSeats);
                })
                .collect(Collectors.toList());
    }

    public List<ShowDto> getShowByMovieAndCity(Long moiveId,String city){
        List<Show> shows = showRepository.findByMovie_IdAndScreen_Theater_city(moiveId,city);
        return  shows.stream()
                .map(show->{
                    List<ShowSeat> availableSeats= showSeatRepository.findByShow_IdAndStatus(show.getId(),"AVAILABLE");
                    return mapToDto(show,availableSeats);
                })
                .collect(Collectors.toList());
    }

    public List<ShowDto> getShowByDateRange(LocalDateTime startDate, LocalDateTime endDate){
        List<Show> shows = showRepository.findByStartTimeBetween(startDate,endDate);
        return  shows.stream()
                .map(show->{
                    List<ShowSeat> availableSeats= showSeatRepository.findByShow_IdAndStatus(show.getId(),"AVAILABLE");
                    return mapToDto(show,availableSeats);
                })
                .collect(Collectors.toList());
    }

    private ShowDto mapToDto(Show show, List<ShowSeat> availableSeats){

        ShowDto showDto = new ShowDto();
        showDto.setId(show.getId());
        showDto.setStartTime(show.getStartTime());
        showDto.setEndTime(show.getEndTime());

        showDto.setMoive(new MoiveDto(
                show.getMovie().getId(),
                show.getMovie().getTitle(),
                show.getMovie().getDescription(),
                show.getMovie().getLanguage(),
                show.getMovie().getGenre(),
                show.getMovie().getDurationMins(),
                show.getMovie().getReleaseDate(),
                show.getMovie().getPosterUrl()
        ));

        TheaterDto theaterDto = new TheaterDto(
                show.getScreen().getTheater().getId(),
                show.getScreen().getTheater().getName(),
                show.getScreen().getTheater().getAddress(),
                show.getScreen().getTheater().getCity(),
                show.getScreen().getTheater().getScreens(),
                show.getScreen().getTheater().getTotalScreen()
        );


        showDto.setScreen(new ScreenDto(
                show.getScreen().getId(),
                show.getScreen().getName(),
                show.getScreen().getTotalSeats(),
                theaterDto
        ));

        List<ShowSeatDto> seatDtos = availableSeats.stream()
                .map(seat -> {
                    ShowSeatDto seatDto = new ShowSeatDto();
                    seatDto.setId(seat.getId());
                    seatDto.setStatus(seat.getStatus());
                    seatDto.setPrice(seat.getPrice());

                    SeatDto baseSeatDto = new SeatDto();
                    baseSeatDto.setId(seat.getSeat().getId());
                    baseSeatDto.setSeatNumber(seat.getSeat().getSeatNumber());
                    baseSeatDto.setSeatType(seat.getSeat().getSeatType());
                    baseSeatDto.setBasePrice(seat.getSeat().getBasePrice());

                    seatDto.setSeat(baseSeatDto);
                    return seatDto;
                })
                .collect(Collectors.toList());

        showDto.setAvailableSeats(seatDtos);
        return showDto;
    }


}
