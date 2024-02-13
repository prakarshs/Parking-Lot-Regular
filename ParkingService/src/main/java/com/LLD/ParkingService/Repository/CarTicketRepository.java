package com.LLD.ParkingService.Repository;

import com.LLD.ParkingService.Entity.CarTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarTicketRepository extends JpaRepository<CarTicket,Long> {
   List<CarTicket> findByCarColor(String color);

 Optional<CarTicket> findByCarRegNumber(String regNo);
}
