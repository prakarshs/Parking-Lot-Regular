package com.LLD.ParkingService.Error;

import lombok.Data;

@Data
public class CustomError extends RuntimeException{
    private String message;
    private String resolution;

    public CustomError(String message, String resolution){
        super(message);
        this.resolution = resolution;
    }
}
