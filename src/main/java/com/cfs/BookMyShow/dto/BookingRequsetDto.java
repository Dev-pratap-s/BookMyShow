package com.cfs.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequsetDto {

    private Long userId;
    private Long ShowId;
    private List<Long> seatIds;
    private String paymentMethod;

}
