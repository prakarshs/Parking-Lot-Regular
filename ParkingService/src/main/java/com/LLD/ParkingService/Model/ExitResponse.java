package com.LLD.ParkingService.Model;

import com.LLD.ParkingService.Entity.SlotDetails;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExitResponse {
    private String text;
    private Long carTicketId;
    private String carColor;
    private String carRegNumber;
    private Duration carParkingDuration;
    private SlotDetails emptySlotDetails;
}
