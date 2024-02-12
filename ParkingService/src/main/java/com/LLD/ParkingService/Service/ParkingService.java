package com.LLD.ParkingService.Service;

import com.LLD.ParkingService.Model.CheckResponse;
import com.LLD.ParkingService.Model.ParkingRequest;
import com.LLD.ParkingService.Model.ParkingResponse;

public interface ParkingService {
    ParkingResponse parkCar(ParkingRequest parkingRequest);

    String generateSlots(Integer numberOfSlots);

    CheckResponse checkSlots();
}
