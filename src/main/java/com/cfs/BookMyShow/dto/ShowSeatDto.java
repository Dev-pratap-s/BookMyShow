package com.cfs.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeatDto {

    private Long id;
    private  SeatDto seat;
    private SeatDto status;
    private Double price;

    public void setStatus(String status) {
    }
}
