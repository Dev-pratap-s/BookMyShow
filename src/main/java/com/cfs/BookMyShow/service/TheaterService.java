package com.cfs.BookMyShow.service;

import com.cfs.BookMyShow.dto.TheaterDto;
import com.cfs.BookMyShow.exception.ResourceNotFoundException;
import com.cfs.BookMyShow.model.Theater;
import com.cfs.BookMyShow.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;


    public TheaterDto createTheater(TheaterDto theaterDto){
        Theater theater = mapToEntity(theaterDto);
         Theater savedTheater=theaterRepository.save(theater);
         return maptoDto(savedTheater);
    }

    public TheaterDto getTheaterById(Long id){
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theater not found"));
        return maptoDto(theater);
    }

    public List<TheaterDto> getAllTheaters(){
        List<Theater> theaters = theaterRepository.findAll();
        return theaters.stream()
                .map(this::maptoDto)
                .collect(Collectors.toList());
    }

    public List<TheaterDto> getAllTheaterByCity(String city){
        List<Theater> theaters = theaterRepository.findByCity(city);
        return theaters.stream()
                .map(this::maptoDto)
                .collect(Collectors.toList());
    }

    /// update todo
    // delete  todo

    private TheaterDto maptoDto(Theater theater) {
        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setId(theater.getId());
        theaterDto.setName(theater.getName());
        theaterDto.setCity(theater.getCity());
        theaterDto.setAddress(theater.getAddress());
        theaterDto.setTotalSeats(String.valueOf(theater.getTotalScreen()));
        return theaterDto;

    }

    private Theater mapToEntity(TheaterDto theaterDto) {
        Theater theater = new Theater();
        theater.setName(theaterDto.getName());
        theater.setAddress(theaterDto.getAddress());
        theater.setCity(theaterDto.getCity());
        theater.setTotalScreen(Integer.valueOf(theaterDto.getTotalSeats()));

        return theater;

    }
}
