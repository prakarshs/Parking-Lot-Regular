package com.LLD.ParkingService.Service;

import com.LLD.ParkingService.Constants.AppConstants;
import com.LLD.ParkingService.Constants.ErrorConstants;
import com.LLD.ParkingService.Entity.CarTicket;
import com.LLD.ParkingService.Entity.SlotDetails;
import com.LLD.ParkingService.Error.CustomError;
import com.LLD.ParkingService.Model.*;
import com.LLD.ParkingService.Repository.CarTicketRepository;
import com.LLD.ParkingService.Repository.SlotRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class ParkingServiceIMPL implements ParkingService{

    @Autowired
    private CarTicketRepository carTicketRepository;

    @Autowired
    private SlotRepository slotRepository;
    @Override
    @Transactional
    public ParkingResponse parkCar(ParkingRequest parkingRequest) {
        log.info("CHECKING FOR EMPTY SLOTS...");
        SlotDetails slotDetails = slotRepository.findFirstByState(AppConstants.VACANT).orElseThrow(()->new CustomError("There Are No Vacant Parking Slots","Try After Some Time."));

        log.info("CREATING CAR TICKET ENTRY...");
        CarTicket carTicket = CarTicket.builder()
                .carRegNumber(parkingRequest.getCarRegNo())
                .carColor(parkingRequest.getCarColor())
                .carParkingTime(Date.from(Instant.now()))
                .slot(slotDetails)
                .build();

        log.info("SAVING CAR TICKET IN DB...");
        try {
            carTicketRepository.save(carTicket);
        }catch (Exception e)
        {
            log.info("CAR REG NUMBER ALREADY EXISTS !");
            throw new CustomError(ErrorConstants.DUPLICATE_CAR_REG,ErrorConstants.TRY_WITH_A_DIFFERENT_CAR);
        }

        log.info("CHANGING SLOT STATE TO OCCUPIED...");
        slotDetails.setState(AppConstants.OCCUPIED);
        slotRepository.save(slotDetails);

        return ParkingResponse.builder()
                .text("!! WELCOME TO PARKKARO !!")
                .carRegNo(carTicket.getCarRegNumber())
                .carColor(carTicket.getCarColor())
                .carParkingTime(carTicket.getCarParkingTime())
                .slotId(carTicket.getSlot().getSlotId())
                .build();
    }

    @Override
    @Transactional
    public String generateSlots(Integer numberOfSlots) {
        log.info("CHECKING IF VALID NUMBER-OF-SLOTS");
        if(numberOfSlots<=0){
            log.error("INVALID NUMBER-OF-SLOTS ENTERED.");
            throw new CustomError(ErrorConstants.INVALID_SLOT_NUMBER, ErrorConstants.TRY_WITH_A_GREATER_NUMBER_OF_SLOTS);
        }
        else {
            log.info("VALID NUMBER-OF-SLOTS");

            for (int i = 1; i <= numberOfSlots; i++) {

                SlotDetails slotDetails = SlotDetails.builder()
                        .state(AppConstants.VACANT)
                        .metresAway(i*10L)
                        .build();
                slotRepository.save(slotDetails);
                log.info("EMPTY SLOT CREATED :" + slotDetails.getSlotId());

            }
        }
        return "REQUIRED NUMBER OF SLOTS HAS BEEN GENERATED !!";
    }

    @Override
    public CheckResponse checkSlots() {

       List<SlotDetails> slotDetailsList = slotRepository.findAll();

       log.info("CHECKING FOR SLOTS ----");
       if(slotDetailsList.isEmpty())
       {
           log.info("THERE ARE NO SLOTS ! PLEASE GENERATE PARKING SLOTS.");
           return CheckResponse.builder()
                   .message("THERE ARE NO SLOTS! GENERATE NEW SLOTS!")
                   .slotList(slotDetailsList)
                   .build();

       }
       log.info("THERE ARE SUFFICIENT STOCKS !");
        return CheckResponse.builder()
                .message("THERE ARE EXISTING SLOTS. WE CAN START PARKING NOW.")
                .slotList(slotDetailsList)
                .build();

    }

    @Override
    public ColorResponse findCarsColor(String color) {
        log.info("CHECKING FOR CARS...");
       List<CarTicket> carTicketList = carTicketRepository.findByCarColor(color);
       log.info("CHECKING FOR EMPTY CAR-LIST...");
       if(carTicketList.isEmpty()){
           log.info("THERE IS NO CAR WITH THE GIVEN COLOR");
           return ColorResponse.builder()
                   .message(" Unfortunately There Are No Cars With Color: "+color)
                   .carList(carTicketList)
                   .build();
       }
        log.info("RETURNING CARS WITH GIVEN COLORS...");
        return ColorResponse.builder()
                .message("Here's Your List Of Cars With Color: "+color)
                .carList(carTicketList)
                .build();
    }

    @Override
    public SlotResponse findSlotsColor(String color) {
        log.info("CHECKING FOR SLOTS WITH GIVEN COLOR CARS...");
        List<CarTicket> carTicketList = carTicketRepository.findByCarColor(color);
        log.info("CHECKING FOR EMPTY CAR-LIST...");
        if(carTicketList.isEmpty()){
            log.info("THERE IS NO CAR WITH THE GIVEN COLOR");
            return SlotResponse.builder()
                    .message(" Unfortunately There Are No Cars With Color: "+color)
                    .slotList(Collections.emptyList())
                    .build();
        }
        log.info("MAKING SLOT LIST FROM CARLIST");
        List<SlotDetails> slotDetailsList = new ArrayList<>();
        for (CarTicket i : carTicketList)
        {
            log.info("Slot Added");
            slotDetailsList.add(i.getSlot());
        }
        log.info("RETURNING SLOTS WITH CARS OF GIVEN COLOR...");
        return SlotResponse.builder()
                .message("Here's Your List Of Slots With Cars Colored: "+color)
                .slotList(slotDetailsList)
                .build();
    }

    @Override
    public ExitResponse exitCar(String regNo) {
        log.info("CHECKING CAR REG NUMBER...");
        CarTicket carTicket = carTicketRepository.findByCarRegNumber(regNo).orElseThrow(()-> new CustomError(ErrorConstants.INVALID_CAR_REG,ErrorConstants.TRY_WITH_A_DIFFERENT_CAR));
        log.info("MARKING CAR SLOT AS VACANT...");
        carTicket.getSlot().setState(AppConstants.VACANT);

        Duration carDuration = Duration.between(Instant.now(),carTicket.getCarParkingTime().toInstant());

        ExitResponse exitResponse = ExitResponse.builder()
                .text(":: THE BELOW CAR IS EXITING ::")
                .carTicketId(carTicket.getCarTicketId())
                .carColor(carTicket.getCarColor())
                .carRegNumber(carTicket.getCarRegNumber())
                .carParkingDuration(carDuration)
                .emptySlotDetails(carTicket.getSlot())
                .build();

        log.info("REMOVING CAR ENTITY FROM DB");
        carTicketRepository.delete(carTicket);
        return exitResponse;
    }
}
