package com.LLD.ParkingService.Service;

import com.LLD.ParkingService.Model.*;

public interface ParkingService {
    ParkingResponse parkCar(ParkingRequest parkingRequest);

    String generateSlots(Integer numberOfSlots);

    CheckResponse checkSlots();

    ColorResponse findCarsColor(String color);

    SlotResponse findSlotsColor(String color);

    ExitResponse exitCar(String regNo);
}
