package com.LLD.ParkingService.Repository;

import com.LLD.ParkingService.Entity.CarTicket;
import com.LLD.ParkingService.Entity.SlotDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends JpaRepository<SlotDetails,Long> {
}
