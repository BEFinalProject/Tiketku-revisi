package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Model.*;
import com.example.tiketku_finalproject.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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

//            TempTransactionEntity existingTransaction = tempTransactionRepository.findBySeatNumberAndAirplane(tempTransaction.getSeats_id(), tempTransaction.getAirplane_name());
//            if (existingTransaction != null) {
//                throw new RuntimeException("Seat number and Airplane you chose already exist. Cannot save the transaction.");
//            }

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
            transactionExist.setDeparture_date(routes.getDeparture_date()); // ini
            transactionExist.setDeparture_time(routes.getDeparture_time());
            transactionExist.setArrival_date(routes.getArrival_date());
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
        updatedTempData.setValid_until(tempTransaction.getDeparture_date());
        return tempTransactionRepository.save(updatedTempData);
    }

//    public List<TempTransactionEntity> addTransaction(List<TempTransactionEntity> tempTransactions) {
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        List<TempTransactionEntity> savedTransactions = new ArrayList<>();
//
//        for (TempTransactionEntity tempTransaction : tempTransactions) {
//            UsersEntity users = usersRepository.getReferenceById(tempTransaction.getUuid_user());
//            SeatsEntity seats = seatsRepository.getReferenceById(tempTransaction.getSeats_id());
//            SchedulesEntity schedules = schedulesRepository.getReferenceById(tempTransaction.getSchedule_uid());
//            AirplanesEntity airplanes = airplaneRepository.getReferenceById(tempTransaction.getAirplane_name());
//            PriceEntity price = priceRepository.getReferenceById(tempTransaction.getId_price());
//
//            TempTransactionEntity existingTransaction = tempTransactionRepository.findBySeatNumberAndAirplane(tempTransaction.getSeats_id(), tempTransaction.getAirplane_name());
//            TempTransactionEntity oneIsExistingTransaction = tempTransactionRepository.findBySeatNumberAndAirplane(tempTransaction.getSeats_id(), tempTransaction.getAirplane_name());
//            if (existingTransaction != null) {
//                throw new RuntimeException("Seat number and Airplane you chose already exist. Cannot save the transaction.");
//            }
////            else if (oneIsExistingTransaction != null) {
////                throw new RuntimeException("One of the reserved seat numbers already exists. Cannot save the transaction.");
////            }
//
//            TempTransactionEntity transactionExist = new TempTransactionEntity();
//            transactionExist.setTransaction_uid(generateUUID());
//            transactionExist.setUuid_user(users.getUuid_user());
//            transactionExist.setSchedule_uid(schedules.getSchedule_uid());
//            transactionExist.setSeats_id(seats.getSeats_id());
//            transactionExist.setDeparture_city(schedules.getDeparture_city());
//            transactionExist.setArrival_city(schedules.getArrival_city());
//            transactionExist.setDeparture_date(schedules.getDeparture_date());
//            transactionExist.setTotal_passenger(tempTransaction.getTotal_passenger());
//            transactionExist.setSeat_class(seats.getSeat_type());
//            transactionExist.setAirplane_name(airplanes.getAirplane_name());
//            transactionExist.setSeat_number(seats.getSeat_number());
//            transactionExist.setCreated_at(currentDateTime);
//            transactionExist.setId_price(price.getId_price());
//            transactionExist.setPrice(price.getPrice());
//
//            savedTransactions.add(tempTransactionRepository.save(transactionExist));
//        }
//
//        return savedTransactions;
//    }


//    public TempTransactionEntity addTransaction(TempTransactionEntity tempTransaction) {
//
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        UsersEntity users = usersRepository.getReferenceById(tempTransaction.getUuid_user());
//        SeatsEntity seats = seatsRepository.getReferenceById(tempTransaction.getSeats_id());
//        SchedulesEntity schedules = schedulesRepository.getReferenceById(tempTransaction.getSchedule_uid());
//        AirplanesEntity airplanes = airplaneRepository.getReferenceById(tempTransaction.getAirplane_name());
//        PriceEntity price = priceRepository.getReferenceById(tempTransaction.getId_price());
////        SeatsEntity seatExsist = seatsRepository.getByStudioSeat(transaction.getStudio_name(), transaction.getNomor_kursi());
//
//
//        TempTransactionEntity existingTransaction = tempTransactionRepository.findBySeatNumberAndAirplane(tempTransaction.getSeats_id(), tempTransaction.getAirplane_name());
//        if (existingTransaction != null) {
//            throw new RuntimeException("Seat number and Airplane u chose already exist. Cannot save the transaction.");
//        }
//        TempTransactionEntity transactionExist = new TempTransactionEntity();
//        transactionExist.setTransaction_uid(generateUUID()); // Menetapkan UUID baru
//        transactionExist.setUuid_user(users.getUuid_user());
//        transactionExist.setSchedule_uid(schedules.getSchedule_uid());
//        transactionExist.setSeats_id(seats.getSeats_id());
//        transactionExist.setDeparture_city(schedules.getDeparture_city());
//        transactionExist.setArrival_city(schedules.getArrival_city());
//        transactionExist.setDeparture_date(schedules.getDeparture_date());
//        transactionExist.setTotal_passenger(tempTransaction.getTotal_passenger());
//        transactionExist.setSeat_class(seats.getSeat_type());
//        transactionExist.setAirplane_name(airplanes.getAirplane_name());
//        transactionExist.setSeat_number(seats.getSeat_number());
//        transactionExist.setCreated_at(currentDateTime);
//        transactionExist.setId_price(price.getId_price());
//        transactionExist.setPrice(price.getPrice());
//
//        return tempTransactionRepository.save(transactionExist);
//
//
//    }

//    public TempTransactionEntity updateTempData(TempTransactionEntity tempTransaction){
//        TempTransactionEntity updatedTempData = tempTransactionRepository.findById(tempTransaction.getTransaction_uid()).get();
//        updatedTempData.setTitle(tempTransaction.getTitle());
//        updatedTempData.setFull_name(tempTransaction.getFull_name());
//        updatedTempData.setGiven_name(tempTransaction.getGiven_name());
//        updatedTempData.setBirth_date(tempTransaction.getBirth_date());
//        updatedTempData.setId_card(tempTransaction.getId_card());
//        updatedTempData.setValid_until(tempTransaction.getDeparture_date());
//        return tempTransactionRepository.save(updatedTempData);
//    }

//    public TempTransactionEntity cancelOrder(TempTransactionEntity tempTransaction){
//        TempTransactionEntity updatedTempData = tempTransactionRepository.findById(tempTransaction.getTransaction_uid()).get();
//        return tempTransactionRepository.save(updatedTempData);
//    }

//    public TempTransactionEntity refundOrder(TempTransactionEntity tempTransaction){
//
//        return null;
//    }

    public void truncate(){
        tempTransactionRepository.deleteAll();
    }

//    public void deleteByStatusUnpaid(){
//        tempTransactionRepository.deleteByStatusUnpaid();
//    }
//
//    public void searchCreatedAt(){
//        tempTransactionRepository.selectCreatedat();
//    }
//    public List<TempTransactionEntity> search(
//            String departure_city, String arrival_city, Date departure_date,
//            int total_passanger,String seat_type){
//        return tempTransactionRepository.searching(departure_city, arrival_city,departure_date, total_passanger,seat_type);
//    }

//    public List<TempTransactionEntity> search(
//            String departure_city, String arrival_city, Date departure_date){
//        return tempTransactionRepository.searching(departure_city,arrival_city, departure_date);
//    }
    private UUID generateUUID() {
        return UUID.randomUUID();
    }
}
