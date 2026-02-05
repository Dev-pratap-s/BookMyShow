package com.cfs.BookMyShow.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Date timestamp;
    private int status;
    private String message;
    private String path;


    public ErrorResponse(Date date, int value, String notFound, String message, String description) {
    }
}
