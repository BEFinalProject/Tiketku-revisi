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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryTransactionService {
    @Autowired
    HistoryTransactionRepository historyTransactionRepository;
    @Autowired
    TempTransactionRepository tempTransactionRepository;
    @Autowired
    UsersRepository usersRepository;
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
            historyData.setPrice(transaction.getPrice());
            historyData.setSeat_type(transaction.getSeat_type());
            historyData.setCreated_at(transaction.getCreated_at());
            historyData.setStatus("Unpaid");

            savedHistory.add(historyTransactionRepository.save(historyData));
        }



        return savedHistory;
    }
}
