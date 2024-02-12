package com.LLD.ParkingService.Model;

import com.LLD.ParkingService.Entity.CarTicket;
import com.LLD.ParkingService.Entity.SlotDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColorResponse {
    private String message;
    private List<CarTicket> carList;
}
