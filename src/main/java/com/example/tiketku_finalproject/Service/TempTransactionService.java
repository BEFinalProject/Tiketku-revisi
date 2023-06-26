package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Model.*;
import com.example.tiketku_finalproject.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TempTransactionService {
    @Autowired
    TempTransactionRepository tempTransactionRepository;
    @Autowired
    SchedulesRepository schedulesRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    SeatsRepository seatsRepository;
    @Autowired
    AirplanesRepository airplaneRepository;
    @Autowired
    PriceRepository priceRepository;
    @Autowired
    RoutesRepository routesRepository;

    public List<TempTransactionEntity> addTransaction(List<TempTransactionEntity> tempTransactions) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneOffset.UTC);
        String formattedDateTime = currentDateTime.format(formatter);

        List<TempTransactionEntity> savedTransactions = new ArrayList<>();

        for (TempTransactionEntity temporaryTransaction : tempTransactions) {
            UsersEntity users = usersRepository.getReferenceById(temporaryTransaction.getUuid_user());
            SchedulesEntity schedules = schedulesRepository.getReferenceById(temporaryTransaction.getUuid_schedules());
            AirplanesEntity airplanes = airplaneRepository.getReferenceById(schedules.getAirplane_id());
            SeatsEntity seats = seatsRepository.getReferenceById(temporaryTransaction.getSeats_id());
            RoutesEntity routes = routesRepository.getReferenceById(schedules.getRoutes_uid());

            TempTransactionEntity transactionExist = new TempTransactionEntity();
            transactionExist.setUuid_transaction(generateUUID());
            transactionExist.setUuid_user(users.getUuid_user());
            transactionExist.setUuid_schedules(schedules.getUuid_schedules());
            transactionExist.setPassenger(1);
            transactionExist.setAirplane_name(airplanes.getAirplane_name());
            transactionExist.setAirplane_type(airplanes.getAirplane_type());
            transactionExist.setDeparture_airport(routes.getDeparture_airport());
            transactionExist.setArrival_airport(routes.getArrival_airport());
            transactionExist.setDeparture_city(routes.getDeparture_city());
            transactionExist.setArrival_city(routes.getArrival_city());
            transactionExist.setDeparture_time(routes.getDeparture_time());
            transactionExist.setArrival_time(routes.getArrival_time()); // sampe sini
            transactionExist.setSeats_id(seats.getSeats_id());
            transactionExist.setPrice(seats.getPrice());
            transactionExist.setSeat_type(seats.getSeat_type());
            transactionExist.setCreated_at(LocalDateTime.parse(formattedDateTime, formatter));

            savedTransactions.add(tempTransactionRepository.save(transactionExist));
        }

        return savedTransactions;
    }

    public TempTransactionEntity updateTempData(TempTransactionEntity tempTransaction){
        TempTransactionEntity updatedTempData = tempTransactionRepository.findById(tempTransaction.getUuid_transaction()).get();
        updatedTempData.setTitle(tempTransaction.getTitle());
        updatedTempData.setFull_name(tempTransaction.getFull_name());
        updatedTempData.setGiven_name(tempTransaction.getGiven_name());
        updatedTempData.setBirth_date(tempTransaction.getBirth_date());
        updatedTempData.setId_card(tempTransaction.getId_card());
        updatedTempData.setValid_until(tempTransaction.getDeparture_time());
        return tempTransactionRepository.save(updatedTempData);
    }
    public void truncate(){
        tempTransactionRepository.deleteAll();
    }

    private UUID generateUUID() {
        return UUID.randomUUID();
    }
}
