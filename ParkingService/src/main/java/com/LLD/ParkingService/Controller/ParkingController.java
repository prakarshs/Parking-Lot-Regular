package com.LLD.ParkingService.Controller;

import com.LLD.ParkingService.Model.ParkingRequest;
import com.LLD.ParkingService.Model.ParkingResponse;
import com.LLD.ParkingService.Service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @PostMapping("/park")
    private ResponseEntity<ParkingResponse> park(@RequestBody ParkingRequest parkingRequest)
}
