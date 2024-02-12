package com.LLD.ParkingService.Controller;

import com.LLD.ParkingService.Model.ColorResponse;
import com.LLD.ParkingService.Model.SlotResponse;
import com.LLD.ParkingService.Service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class FilterController {
    @Autowired
    private ParkingService parkingService;

    @GetMapping("/findCars/{color}")
    private ResponseEntity<ColorResponse> findCarsColor(@PathVariable String color){
        return new ResponseEntity<>(parkingService.findCarsColor(color), HttpStatus.OK);
    }
    @GetMapping("/findSlots/{color}")
    private ResponseEntity<SlotResponse> findSlotsColor(@PathVariable String color){
        return new ResponseEntity<>(parkingService.findSlotsColor(color), HttpStatus.OK);
    }

}
