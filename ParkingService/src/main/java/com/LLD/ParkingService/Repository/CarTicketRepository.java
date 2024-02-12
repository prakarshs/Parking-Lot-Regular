package com.LLD.ParkingService.Repository;

import com.LLD.ParkingService.Entity.CarTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarTicketRepository extends JpaRepository<CarTicket,Long> {
}
