package com.LLD.ParkingService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "CAR_TICKET_DETAILS")
public class CarTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carTicketId;

    private String carColor;
    @Column(unique = true)
    private String carRegNumber;
    private Date carParkingTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SLOT", nullable = false, foreignKey = @ForeignKey(name = "FK_SLOT_DETAILS"))
    private SlotDetails slot;
}
