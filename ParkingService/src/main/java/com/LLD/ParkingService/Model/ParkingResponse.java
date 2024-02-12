package com.LLD.ParkingService.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingResponse {
    private String text;
    private String carTicketId;
    private String carRegNo;
    private String carColor;
    private Long slotId;
    private Date carParkingTime;

}
