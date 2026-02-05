package com.cfs.BookMyShow.dto;

import com.cfs.BookMyShow.model.Screen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheaterDto {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String totalSeats;

    public TheaterDto(long id, String name, String address, String city, List<Screen> screens, Integer totalScreen) {

    }
}
