package com.cfs.BookMyShow.controller;

import com.cfs.BookMyShow.dto.MoiveDto;
import com.cfs.BookMyShow.service.MoiveService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {

    @Autowired
    private MoiveService moiveService;

    @PostMapping
    public ResponseEntity<MoiveDto> addMovie(
            @Valid @RequestBody MoiveDto moiveDto) {

        return new ResponseEntity<>(
                moiveService.createMovie(moiveDto),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoiveDto> getMovieById(@PathVariable Long id){
        return ResponseEntity.ok(moiveService.getMoiveById(id));
    }

    @GetMapping
    public ResponseEntity<List<MoiveDto>> getAllMovies()
    {
        return ResponseEntity.ok(moiveService.getAllMovies());

    }
}
