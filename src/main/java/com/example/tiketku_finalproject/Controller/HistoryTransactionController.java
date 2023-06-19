package com.example.tiketku_finalproject.Controller;

import com.example.tiketku_finalproject.Model.HistoryTransactionEntity;
import com.example.tiketku_finalproject.Response.CommonResponse;
import com.example.tiketku_finalproject.Response.CommonResponseGenerator;
import com.example.tiketku_finalproject.Service.HistoryTransactionService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value ="/HistoryTransaction")
@Api(value = "HistoryTransaction")
public class HistoryTransactionController {
    @Autowired
    HistoryTransactionService historyTransactionService;

    @Autowired
    CommonResponseGenerator commonResponseGenerator;

    @GetMapping(value = "/user/{uuid_user}")
    @Operation(description = "Menampilkan Users Transaksi")
    public CommonResponse<List<HistoryTransactionEntity>> getHistory(@PathVariable UUID uuid_user){
        try {
            List<HistoryTransactionEntity> historyTransaction = historyTransactionService.searchHistoryUsers(uuid_user);
            log.info(String.valueOf(historyTransaction));

            if(!historyTransaction.isEmpty()) {
                return commonResponseGenerator.succsesResponse(historyTransaction,"Sukses Mencari Jadwal Transaction");
            }else {
                return commonResponseGenerator.succsesResponse(historyTransaction, "Data tidak ditemukan");
            }
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }
    @GetMapping(value = "/date/{departure_date}/{uuid_user}")
    @Operation(description = "Menampilkan Tanggal Transaksi")
    public CommonResponse<List<HistoryTransactionEntity>> getHistory(@PathVariable Date departure_date, @PathVariable UUID uuid_user){
        try {
            List<HistoryTransactionEntity> historyTransaction = historyTransactionService.searchHistoryByDateAndUUIDUsers(departure_date,uuid_user);
            log.info(String.valueOf(historyTransaction));
            if (!historyTransaction.isEmpty()) {
                return commonResponseGenerator.succsesResponse(historyTransaction,"Sukses Mencari Jadwal Transaction");
            }else {
                return commonResponseGenerator.succsesResponse(historyTransaction, "Data tidak ditemukan");
            }
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }
    @GetMapping(value = "/UUID/{uuid_user}/{uuid_history}")
    @Operation(description = "Menampilkan Tanggal Transaksi")
    public CommonResponse<List<HistoryTransactionEntity>> getHistory(@PathVariable UUID uuid_user, @PathVariable UUID uuid_history){
        try {
            List<HistoryTransactionEntity> historyTransaction = historyTransactionService.searchHistoryByUUIDUserAndHistory(uuid_user,uuid_history);
            log.info(String.valueOf(historyTransaction));

            if(!historyTransaction.isEmpty()) {
                return commonResponseGenerator.succsesResponse(historyTransaction,"Sukses Mencari Jadwal Transaction");
            }else {
                return commonResponseGenerator.succsesResponse(historyTransaction, "Data tidak ditemukan");
            }
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }
}
