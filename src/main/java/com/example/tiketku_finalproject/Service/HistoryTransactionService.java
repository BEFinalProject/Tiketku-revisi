package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Model.HistoryTransactionEntity;
import com.example.tiketku_finalproject.Model.SchedulesEntity;
import com.example.tiketku_finalproject.Model.TempTransactionEntity;
import com.example.tiketku_finalproject.Repository.HistoryTransactionRepository;
import com.example.tiketku_finalproject.Repository.SchedulesRepository;
import com.example.tiketku_finalproject.Repository.TempTransactionRepository;
import com.example.tiketku_finalproject.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HistoryTransactionService {
    @Autowired
    HistoryTransactionRepository historyTransactionRepository;
    @Autowired
    TempTransactionRepository tempTransactionRepository;
    @Autowired
    UsersRepository usersRepository;
    private LocalDateTime currentDateTime = LocalDateTime.now();

    public List<HistoryTransactionEntity> searchHistoryUsers(UUID uuid_user){
        return historyTransactionRepository.findByUUIDUsers(uuid_user);
    }
    public List<HistoryTransactionEntity> searchHistoryByDateAndUUIDUsers(Date departure_date, UUID uuid_users){
        return historyTransactionRepository.findByDepartureDate(departure_date, uuid_users);
    }
    public List<HistoryTransactionEntity> searchHistoryByUUIDUserAndHistory(UUID uuid_user, UUID uuid_history){
        return historyTransactionRepository.findByUUIDUserAndHistory(uuid_user, uuid_history);
    }

    public List<HistoryTransactionEntity> saveDataHistory(List<HistoryTransactionEntity> historyTransaction) {
        List<HistoryTransactionEntity> savedHistory = new ArrayList<>();

        for (HistoryTransactionEntity tempHistory : historyTransaction) {
            TempTransactionEntity transaction = tempTransactionRepository.getReferenceById(tempHistory.getUuid_history());
            TempTransactionEntity transactionExsist = tempTransactionRepository.findById(tempHistory.getUuid_history()).orElse(null);
            if (transactionExsist == null) {
                throw new RuntimeException("Transaksi tidak ditemukan");
            }

            HistoryTransactionEntity historyData = new HistoryTransactionEntity();
            historyData.setUuid_history(transaction.getUuid_transaction());
            historyData.setUuid_user(transaction.getUuid_user());
            historyData.setUuid_schedules(transaction.getUuid_schedules());
            historyData.setPassenger(1);
            historyData.setAirplane_name(transaction.getAirplane_name());
            historyData.setAirplane_type(transaction.getAirplane_type());
            historyData.setDeparture_airport(transaction.getDeparture_airport());
            historyData.setArrival_airport(transaction.getArrival_airport());
            historyData.setDeparture_city(transaction.getDeparture_city());
            historyData.setArrival_city(transaction.getArrival_city());
            historyData.setDeparture_date(transaction.getDeparture_date());
            historyData.setArrival_date(transaction.getArrival_date());
            historyData.setDeparture_time(transaction.getDeparture_time());
            historyData.setArrival_time(transaction.getArrival_time());
            historyData.setPrice(transaction.getPrice());
            historyData.setSeat_type(transaction.getSeat_type());
            historyData.setCreated_at(transaction.getCreated_at());
            historyData.setStatus("Unpaid");

            savedHistory.add(historyTransactionRepository.save(historyData));
        }



        return savedHistory;
    }

    public HistoryTransactionEntity updateDataHistory(HistoryTransactionEntity historyTransaction){
//        TempTransactionEntity updateTempTranscation = tempTransactionRepository.findById(historyTransaction.getTransaction_uid()).get();
//        updateTempTranscation.

        TempTransactionEntity transaction = tempTransactionRepository.getReferenceById(historyTransaction.getUuid_history());
        HistoryTransactionEntity historyTransactionExists = historyTransactionRepository.findById(historyTransaction.getUuid_history()).orElse(null);
        if (historyTransactionExists == null) {
            throw new RuntimeException("History Transaksi tidak ditemukan");
        }

        HistoryTransactionEntity historyData = new HistoryTransactionEntity();
        historyData.setUuid_history(transaction.getUuid_transaction());
        historyData.setUuid_schedules(transaction.getUuid_schedules());
        historyData.setUuid_user(transaction.getUuid_user());
        historyData.setPassenger(transaction.getPassenger());
        historyData.setAirplane_name(transaction.getAirplane_name());
        historyData.setAirplane_type(transaction.getAirplane_type());
        historyData.setDeparture_airport(transaction.getDeparture_airport());
        historyData.setArrival_airport(transaction.getArrival_airport());
        historyData.setDeparture_city(transaction.getDeparture_city());
        historyData.setArrival_city(transaction.getArrival_city());
        historyData.setDeparture_date(transaction.getDeparture_date());
        historyData.setArrival_date(transaction.getArrival_date());
        historyData.setDeparture_time(transaction.getDeparture_time());
        historyData.setArrival_time(transaction.getArrival_time());
        historyData.setPrice(transaction.getPrice());
        historyData.setSeat_type(transaction.getSeat_type());
        historyData.setStatus("Paid");
        historyData.setTitle(transaction.getTitle());
        historyData.setFull_name(transaction.getFull_name());
        historyData.setGiven_name(transaction.getGiven_name());
        historyData.setBirth_date(transaction.getBirth_date());
        historyData.setId_card(transaction.getId_card());
        historyData.setValid_until(transaction.getDeparture_date());
        historyData.setCreated_at(transaction.getCreated_at());
        historyData.setModified_at(currentDateTime);
        return historyTransactionRepository.save(historyData);
    }

    public HistoryTransactionEntity cancelOrder(HistoryTransactionEntity historyTransaction){
        TempTransactionEntity transaction = tempTransactionRepository.getReferenceById(historyTransaction.getUuid_history());
        HistoryTransactionEntity historyTransactionExists = historyTransactionRepository.findById(historyTransaction.getUuid_history()).orElse(null);
        if (historyTransactionExists == null) {
            throw new RuntimeException("History Transaksi tidak ditemukan");
        }

        HistoryTransactionEntity historyData = new HistoryTransactionEntity();
        historyData.setUuid_history(transaction.getUuid_transaction());
        historyData.setUuid_schedules(transaction.getUuid_schedules());
        historyData.setUuid_user(transaction.getUuid_user());
        historyData.setPassenger(transaction.getPassenger());
        historyData.setAirplane_name(transaction.getAirplane_name());
        historyData.setAirplane_type(transaction.getAirplane_type());
        historyData.setDeparture_airport(transaction.getDeparture_airport());
        historyData.setArrival_airport(transaction.getArrival_airport());
        historyData.setDeparture_city(transaction.getDeparture_city());
        historyData.setArrival_city(transaction.getArrival_city());
        historyData.setDeparture_date(transaction.getDeparture_date());
        historyData.setArrival_date(transaction.getArrival_date());
        historyData.setDeparture_time(transaction.getDeparture_time());
        historyData.setArrival_time(transaction.getArrival_time());
        historyData.setPrice(transaction.getPrice());
        historyData.setSeat_type(transaction.getSeat_type());
        historyData.setStatus("Canceled");
        historyData.setTitle(transaction.getTitle());
        historyData.setFull_name(transaction.getFull_name());
        historyData.setGiven_name(transaction.getGiven_name());
        historyData.setBirth_date(transaction.getBirth_date());
        historyData.setId_card(transaction.getId_card());
        historyData.setValid_until(transaction.getDeparture_date());
        historyData.setCreated_at(transaction.getCreated_at());
        historyData.setModified_at(currentDateTime);
        return historyTransactionRepository.save(historyData);
    }

    public HistoryTransactionEntity refundOrder(HistoryTransactionEntity historyTransaction) {
        TempTransactionEntity transaction = tempTransactionRepository.getReferenceById(historyTransaction.getUuid_history());
        HistoryTransactionEntity historyTransactionExists = historyTransactionRepository.findById(historyTransaction.getUuid_history()).orElse(null);
        if (historyTransactionExists == null) {
            throw new RuntimeException("History transaction does not exist");
        }

        LocalDateTime createdDateTime = transaction.getCreated_at();
        LocalDateTime currentDateTime = LocalDateTime.now();

        long hoursDifference = ChronoUnit.HOURS.between(createdDateTime, currentDateTime);
        if (historyTransactionExists.getStatus().equals("Canceled") && hoursDifference < 2) {
            throw new RuntimeException("Tidak bisa merefund pembayaran yang sudah di cancel!");
        }
        if (hoursDifference >= 2) {
            throw new RuntimeException("Melewati batas waktu refund");
        }

        HistoryTransactionEntity historyData = new HistoryTransactionEntity();
        historyData.setUuid_history(transaction.getUuid_transaction());
        historyData.setUuid_schedules(transaction.getUuid_schedules());
        historyData.setUuid_user(transaction.getUuid_user());
        historyData.setPassenger(transaction.getPassenger());
        historyData.setAirplane_name(transaction.getAirplane_name());
        historyData.setAirplane_type(transaction.getAirplane_type());
        historyData.setDeparture_airport(transaction.getDeparture_airport());
        historyData.setArrival_airport(transaction.getArrival_airport());
        historyData.setDeparture_city(transaction.getDeparture_city());
        historyData.setArrival_city(transaction.getArrival_city());
        historyData.setDeparture_date(transaction.getDeparture_date());
        historyData.setArrival_date(transaction.getArrival_date());
        historyData.setDeparture_time(transaction.getDeparture_time());
        historyData.setArrival_time(transaction.getArrival_time());
        historyData.setPrice(transaction.getPrice());
        historyData.setSeat_type(transaction.getSeat_type());
        historyData.setStatus("Refunded");
        historyData.setTitle(transaction.getTitle());
        historyData.setFull_name(transaction.getFull_name());
        historyData.setGiven_name(transaction.getGiven_name());
        historyData.setBirth_date(transaction.getBirth_date());
        historyData.setId_card(transaction.getId_card());
        historyData.setValid_until(transaction.getDeparture_date());
        historyData.setCreated_at(transaction.getCreated_at());
        historyData.setModified_at(currentDateTime);

        return historyTransactionRepository.save(historyData);
    }

}
