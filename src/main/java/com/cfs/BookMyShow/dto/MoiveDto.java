package com.cfs.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoiveDto {
    private Long id;
    private String title;
    private String description;
    private String language;
    private String genre;
    private Integer durationMins;
    private String reeaseDate;
    private String posterUrl;

    public MoiveDto(long id, String title, String description, String language, String genre, String durationMins, String releaseDate, String posterUrl) {



    }
}
