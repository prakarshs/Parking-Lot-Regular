package com.LLD.ParkingService.Controller;

import com.LLD.ParkingService.Model.CheckResponse;
import com.LLD.ParkingService.Model.ExitResponse;
import com.LLD.ParkingService.Model.ParkingRequest;
import com.LLD.ParkingService.Model.ParkingResponse;
import com.LLD.ParkingService.Service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @PostMapping("/park")
    private ResponseEntity<ParkingResponse> park(@RequestBody ParkingRequest parkingRequest){
      return new ResponseEntity<>(parkingService.parkCar(parkingRequest), HttpStatus.OK);
    }
    @PostMapping("/createSlots")
    private ResponseEntity<String> park(@RequestParam(name = "numberOfSlots") Integer numberOfSlots){
      return new ResponseEntity<>(parkingService.generateSlots(numberOfSlots), HttpStatus.OK);
    }

    @GetMapping("/initialChecks")
    private ResponseEntity<CheckResponse> check(){
        return new ResponseEntity<>(parkingService.checkSlots(),HttpStatus.OK);
    }

    @PutMapping("/exit")
    private ResponseEntity<ExitResponse> exit(@RequestParam("regNo") String regNo){
        return new ResponseEntity<>(parkingService.exitCar(regNo),HttpStatus.OK);
    }

}
