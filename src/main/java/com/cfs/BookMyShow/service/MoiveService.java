package com.cfs.BookMyShow.service;

import com.cfs.BookMyShow.dto.MoiveDto;
import com.cfs.BookMyShow.exception.ResourceNotFoundException;
import com.cfs.BookMyShow.model.Movie;
import com.cfs.BookMyShow.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoiveService {

    @Autowired
    private MovieRepository movieRepository;

    public MoiveDto createMovie(MoiveDto moiveDto){
        Movie movie = mapToEntity(moiveDto);
        Movie saveMovie = movieRepository.save(movie);
        return mapToDto(saveMovie);
    }

    public MoiveDto getMoiveById(Long id){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("moive not found"+ id));
            return mapToDto(movie);
    }

    public List<MoiveDto> getAllMovies(){
        List<Movie> movie = movieRepository.findAll();

        return movie.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<MoiveDto> getMovieByLanguage(String language){
        List<Movie> movies = movieRepository.findByLanguage(language);

        return movies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }


    public List<MoiveDto> getMovieByGenre(String genre){
        List<Movie> movies = movieRepository.findByLanguage(genre);

        return movies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }

    public List<MoiveDto> getMovieByTitle(String title){
        List<Movie> movies = movieRepository.findByLanguage(title);

        return movies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }


    public MoiveDto updateMovie(Long id , MoiveDto moiveDto){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("movie id found with id"));
        movie.setTitle(moiveDto.getTitle());
        movie.setDescription(moiveDto.getDescription());
        movie.setLanguage(moiveDto.getLanguage());
        movie.setGenre(moiveDto.getGenre());
        movie.setDurationMins(moiveDto.getDescription());
        movie.setReleaseDate(moiveDto.getReeaseDate());
        movie.setPosterUrl(moiveDto.getPosterUrl());

         Movie updateMovie = movieRepository.save(movie);
         return mapToDto(updateMovie);
    }

    public void deleteMovie(Long id){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("movie id found with id"));
        movieRepository.delete(movie);
    }



    private MoiveDto mapToDto(Movie movie){
        MoiveDto moiveDto = new MoiveDto();
        moiveDto.setId(movie.getId());
        moiveDto.setTitle(movie.getTitle());
        moiveDto.setDescription(movie.getDescription());
        moiveDto.setLanguage(movie.getLanguage());
        moiveDto.setGenre(movie.getGenre());
        moiveDto.setDurationMins(Integer.valueOf(movie.getDurationMins()));
        moiveDto.setReeaseDate(movie.getReleaseDate());
        moiveDto.setPosterUrl(movie.getPosterUrl());

        return moiveDto;

    }

    public Movie mapToEntity(MoiveDto moiveDto){
        Movie movie = new Movie();
        movie.setTitle(moiveDto.getTitle());
        movie.setDescription(moiveDto.getDescription());
        movie.setLanguage(moiveDto.getLanguage());
        movie.setGenre(moiveDto.getGenre());
        movie.setDurationMins(moiveDto.getDescription());
        movie.setReleaseDate(moiveDto.getReeaseDate());
        movie.setPosterUrl(moiveDto.getPosterUrl());

        return movie;
    }
}
