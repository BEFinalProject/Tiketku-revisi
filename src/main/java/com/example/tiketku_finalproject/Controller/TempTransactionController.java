package com.example.tiketku_finalproject.Controller;

import com.example.tiketku_finalproject.Model.HistoryTransactionEntity;
import com.example.tiketku_finalproject.Model.SchedulesEntity;
import com.example.tiketku_finalproject.Model.TempTransactionEntity;
import com.example.tiketku_finalproject.Response.*;
import com.example.tiketku_finalproject.Service.HistoryTransactionService;
import com.example.tiketku_finalproject.Service.SchedulesService;
import com.example.tiketku_finalproject.Service.TempTransactionService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value ="/TempTransacation")
@Api(value = "TempTransacation")
public class TempTransactionController {

    @Autowired
    TempTransactionService tempTransactionService;
    @Autowired
    CommonResponseGenerator commonResponseGenerator;
//    @Autowired
//    HistoryTransactionService historyTransactionService;
    @Autowired
    SchedulesService schedulesService;
    @Autowired
    HistoryTransactionService historyTransactionService;
//    @Autowired
//    SchedulesRepository schedulesRepository;


    @PostMapping(value = "/addTempTransaction")
    @Operation(description = "")
    public CommonResponse<List<TempTransactionEntity>> addTransaction(@RequestBody List<TempAddTransactionResponse> param) {
        try {
            List<TempTransactionEntity> transactionEntities = new ArrayList<>();
            List<SchedulesEntity> schedulesEntities = new ArrayList<>();

            for (TempAddTransactionResponse tempAddTransaction : param) {
                TempTransactionEntity tempTransaction = new TempTransactionEntity();
                tempTransaction.setUuid_user(tempAddTransaction.getUuid_user());
                tempTransaction.setUuid_schedules(tempAddTransaction.getSchedule_uid());
                tempTransaction.setSeats_id(tempAddTransaction.getSeats_id());
                transactionEntities.add(tempTransaction);

//                HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
//                historyTransaction.setUuid_history(tempTransaction.getUuid_transaction());
//                historyTransactions.add(historyTransaction);

//                log.info(String.valueOf(historyTransaction));

                Optional<SchedulesEntity> optionalSchedulesEntity = schedulesService.getByUuidSchedules(tempAddTransaction.getSchedule_uid());

                if (optionalSchedulesEntity.isPresent()) {
                    SchedulesEntity schedulesEntity = optionalSchedulesEntity.get();
                    int updatedLimits = schedulesEntity.getLimits() - 1;

                    if (updatedLimits < 0) {
                        String message = "Limit exceeded. Data not saved for TempTransaction with UUID: " + tempAddTransaction.getSchedule_uid();
                        log.info(message);
                        throw new Exception(message);
                    } else {
                        schedulesEntity.setLimits(updatedLimits);
                        schedulesEntities.add(schedulesEntity);
                        log.info(String.valueOf(schedulesEntities));

                        // Create and save the TempTransactionEntity
                        List<TempTransactionEntity> savedTempTransactions = tempTransactionService.addTransaction(Collections.singletonList(tempTransaction));
                        TempTransactionEntity savedTempTransaction = savedTempTransactions.get(0);
                        tempTransaction.setUuid_transaction(savedTempTransaction.getUuid_transaction());
                    }
                }

            }

            // ini yang bener
//            List<HistoryTransactionEntity> historyTransactions = new ArrayList<>();
//            for (TempTransactionEntity savedTransaction : param) {
//                HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
//                historyTransaction.setUuid_history(savedTransaction.getUuid_transaction());
//                historyTransactions.add(historyTransaction);
//            }
//            List<HistoryTransactionEntity> savedHistory = historyTransactionService.saveDataHistory(historyTransactions);

            // ini yang bener
            List<HistoryTransactionEntity> historyTransactions = new ArrayList<>();
            for (TempTransactionEntity savedTransaction : transactionEntities) {
                HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
                historyTransaction.setUuid_history(savedTransaction.getUuid_transaction());
                historyTransactions.add(historyTransaction);
            }
            List<HistoryTransactionEntity> savedHistory = historyTransactionService.saveDataHistory(historyTransactions);


            // Simpan historyTransactions ke dalam database menggunakan historyTransactionService.saveDataHistory
           // List<HistoryTransactionEntity> savedHistoryTransactions = historyTransactionService.saveDataHistory(historyTransactions);

            // Save schedulesEntities only if there are valid data
            if (!schedulesEntities.isEmpty()) {
                List<SchedulesEntity> savedLimitDown = schedulesService.saveDataLimit(schedulesEntities);

                // Remove TempTransactionEntity entries for which the limit was exceeded
                transactionEntities.removeIf(tempTransaction -> {
                    for (SchedulesEntity schedulesEntity : savedLimitDown) {
                        if (tempTransaction.getUuid_schedules().equals(schedulesEntity.getUuid_schedules())) {
                            return false;
                        }
                    }
                    return true;
                });
            }

            log.info(String.valueOf(transactionEntities));
            return commonResponseGenerator.succsesResponse(transactionEntities, "Sukses Menambahkan Data");
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @PutMapping(value = "/checkout")
    @Operation(description = "Update a temporary transaction to paid status")
    public CommonResponse<TempTransactionEntity> checkoutTransaction(@RequestBody CheckoutTransactionResponse param){
        try {
            TempTransactionEntity tempTransaction = new TempTransactionEntity();
            tempTransaction.setUuid_transaction(param.getUuid_transaction());
            tempTransaction.setTitle(param.getTitle());
            tempTransaction.setFull_name(param.getFull_name());
            tempTransaction.setGiven_name(param.getGiven_name());
            tempTransaction.setBirth_date(param.getBirth_date());
            tempTransaction.setId_card(param.getId_card());
            TempTransactionEntity tempTransactionEntity = tempTransactionService.updateTempData(tempTransaction);

            HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
            historyTransaction.setUuid_history(tempTransactionEntity.getUuid_transaction());
            log.info(String.valueOf(historyTransaction));
            HistoryTransactionEntity saveHistory = historyTransactionService.updateDataHistory(historyTransaction);

            log.info(String.valueOf(tempTransaction));
            log.info(String.valueOf(tempTransactionEntity), "Successfully updated " + param.getFull_name());
            return commonResponseGenerator.succsesResponse(tempTransactionEntity, "Successfully updated " + param.getUuid_transaction());
        }catch (Exception e){
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }


    @PutMapping(value = "/cancelCheckout")
    @Operation(description = "Update a temporary transaction to cancel status")
    public CommonResponse<TempTransactionEntity> cancelCheckout(@RequestBody CancelAndRefundCheckoutResponse param) {
        try {
            HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
            historyTransaction.setUuid_history(param.getUuid_transaction());
            HistoryTransactionEntity savedHistory = historyTransactionService.cancelOrder(historyTransaction);

            TempTransactionEntity tempTransaction = new TempTransactionEntity();
            tempTransaction.setUuid_transaction(savedHistory.getUuid_history());
            tempTransaction.setTitle(savedHistory.getTitle());
            tempTransaction.setFull_name(savedHistory.getFull_name());
            tempTransaction.setGiven_name(savedHistory.getGiven_name());
            tempTransaction.setBirth_date(savedHistory.getBirth_date());
            tempTransaction.setId_card(savedHistory.getId_card());
            TempTransactionEntity updatedTempTransaction = tempTransactionService.updateTempData(tempTransaction);

            log.info("Successfully updated " + param.getUuid_transaction());
            return commonResponseGenerator.succsesResponse(updatedTempTransaction, "Successfully updated status to canceled");
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @PutMapping(value = "/refundCheckout")
    @Operation(description = "Update a temporary transaction to cancel refund")
    public CommonResponse<TempTransactionEntity> refundCheckout(@RequestBody CancelAndRefundCheckoutResponse param) {
        try {
            HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
            historyTransaction.setUuid_history(param.getUuid_transaction());
            log.info(String.valueOf(historyTransaction));
            HistoryTransactionEntity savedHistory = historyTransactionService.refundOrder(historyTransaction);

            TempTransactionEntity tempTransaction = new TempTransactionEntity();
            tempTransaction.setUuid_transaction(savedHistory.getUuid_history());
            tempTransaction.setTitle(savedHistory.getTitle());
            tempTransaction.setFull_name(savedHistory.getFull_name());
            tempTransaction.setGiven_name(savedHistory.getGiven_name());
            tempTransaction.setBirth_date(savedHistory.getBirth_date());
            tempTransaction.setId_card(savedHistory.getId_card());
            TempTransactionEntity updatedTempTransaction = tempTransactionService.updateTempData(tempTransaction);

            log.info("Successfully updated " + param.getUuid_transaction());
            return commonResponseGenerator.succsesResponse(updatedTempTransaction, "Successfully updated status to refunded");
        } catch (Exception e) {
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    /*@PutMapping(value = "/cancelCheckout")
    @Operation(description = "Update a temporary transaction to cancel status")
    public CommonResponse<TempTransactionEntity> cancelCheckout(@RequestBody CancelAndRefundCheckoutResponse param){
        try {
            TempTransactionEntity tempTransaction = new TempTransactionEntity();
            tempTransaction.setUuid_transaction(param.getUuid_transaction());
            TempTransactionEntity tempTransactionEntity = tempTransactionService.updateTempData(tempTransaction);

            HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
            historyTransaction.setUuid_history(tempTransactionEntity.getUuid_transaction());
            log.info(String.valueOf(historyTransaction));
            HistoryTransactionEntity saveHistory = historyTransactionService.cancelOrder(historyTransaction);

            log.info(String.valueOf(tempTransaction));
            log.info(String.valueOf(tempTransactionEntity), "Successfully updated " + param.getUuid_transaction());
            return commonResponseGenerator.succsesResponse(tempTransactionEntity, "Successfully updated status to canceled");
        }catch (Exception e){
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }*/


    /*@PutMapping(value = "/refundCheckout")
    @Operation(description = "Update a temporary transaction to cancel refund")
    public CommonResponse<TempTransactionEntity> refundCheckout(@RequestBody CancelAndRefundCheckoutResponse param){
        try {
            TempTransactionEntity tempTransaction = new TempTransactionEntity();
            tempTransaction.setUuid_transaction(param.getUuid_transaction());
            TempTransactionEntity tempTransactionEntity = tempTransactionService.updateTempData(tempTransaction);

            HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
            historyTransaction.setUuid_history(tempTransactionEntity.getUuid_transaction());
            log.info(String.valueOf(historyTransaction));
            HistoryTransactionEntity saveHistory = historyTransactionService.refundOrder(historyTransaction);

            log.info(String.valueOf(tempTransaction));
            log.info(String.valueOf(tempTransactionEntity), "Successfully updated " + param.getUuid_transaction());
            return commonResponseGenerator.succsesResponse(tempTransactionEntity, "Successfully updated status to refunded");
        }catch (Exception e){
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }*/



    // sudah bisa tapi respon body kacau
//    @PostMapping(value = "/addTempTransaction")
//    @Operation(description = "")
//    public CommonResponse<List<TempTransactionEntity>> addTransaction(@RequestBody List<TempAddTransactionResponse> param) {
//        try {
//            List<TempTransactionEntity> transactionEntities = new ArrayList<>();
//            List<SchedulesEntity> schedulesEntities = new ArrayList<>();
//
//            for (TempAddTransactionResponse tempAddTransaction : param) {
//                TempTransactionEntity tempTransaction = new TempTransactionEntity();
//                tempTransaction.setUuid_user(tempAddTransaction.getUuid_user());
//                tempTransaction.setUuid_schedules(tempAddTransaction.getSchedule_uid());
//                tempTransaction.setPassenger(tempAddTransaction.getPassanger());
//                transactionEntities.add(tempTransaction);
//
//                Optional<SchedulesEntity> optionalSchedulesEntity = schedulesService.getByUuidSchedules(tempAddTransaction.getSchedule_uid());
//
//                if (optionalSchedulesEntity.isPresent()) {
//                    SchedulesEntity schedulesEntity = optionalSchedulesEntity.get();
//                    int updatedLimits = schedulesEntity.getLimits() - tempAddTransaction.getPassanger();
//
//                    if (updatedLimits < 0) {
//                        log.info("Limit exceeded. Data not saved for TempTransaction with UUID: " + tempAddTransaction.getSchedule_uid());
//                    } else {
//                        schedulesEntity.setLimits(updatedLimits);
//                        schedulesEntities.add(schedulesEntity);
//                        log.info(String.valueOf(schedulesEntities));
//
//                        // Create and save the TempTransactionEntity
//                        List<TempTransactionEntity> savedTempTransactions = tempTransactionService.addTransaction(Collections.singletonList(tempTransaction));
//                        TempTransactionEntity savedTempTransaction = savedTempTransactions.get(0);
//                        tempTransaction.setUuid_transaction(savedTempTransaction.getUuid_transaction());
//                    }
//                }
//            }
//
//            // Save schedulesEntities only if there are valid data
//            if (!schedulesEntities.isEmpty()) {
//                List<SchedulesEntity> savedLimitDown = schedulesService.saveDataLimit(schedulesEntities);
//
//                // Remove TempTransactionEntity entries for which the limit was exceeded
//                transactionEntities.removeIf(tempTransaction -> {
//                    for (SchedulesEntity schedulesEntity : savedLimitDown) {
//                        if (tempTransaction.getUuid_schedules().equals(schedulesEntity.getUuid_schedules())) {
//                            return false;
//                        }
//                    }
//                    return true;
//                });
//            }
//
//            log.info(String.valueOf(transactionEntities));
//            return commonResponseGenerator.succsesResponse(transactionEntities, "Sukses Menambahkan Data");
//        } catch (Exception e) {
//            log.warn(String.valueOf(e));
//            return commonResponseGenerator.failedResponse(e.getMessage());
//        }
//    }

//bagian bawah ini sudah bisa tapi belum ada perkodisian

//    @PostMapping(value = "/addTempTransaction")
//    @Operation(description = "")
//    public CommonResponse<List<TempTransactionEntity>> addTransaction(@RequestBody List<TempAddTransactionResponse> param) {
//        try {
//            List<TempTransactionEntity> transactionEntities = new ArrayList<>();
//
//            for (TempAddTransactionResponse tempAddTransaction : param) {
//                TempTransactionEntity tempTransaction = new TempTransactionEntity();
//                tempTransaction.setUuid_user(tempAddTransaction.getUuid_user());
//                tempTransaction.setUuid_schedules(tempAddTransaction.getSchedule_uid());
//                tempTransaction.setPassenger(tempAddTransaction.getPassanger());
//                transactionEntities.add(tempTransaction);
//            }
//
//            List<TempTransactionEntity> savedTransactions = tempTransactionService.addTransaction(transactionEntities);
//
//            List<SchedulesEntity> schedulesEntities = new ArrayList<>();
//            for (TempTransactionEntity limitDown : savedTransactions) {
//                Optional<SchedulesEntity> optionalSchedulesEntity = schedulesService.getByUuidSchedules(limitDown.getUuid_schedules());
//                if (optionalSchedulesEntity.isPresent()) {
//                    SchedulesEntity schedulesEntity = optionalSchedulesEntity.get();
//                    int updatedLimits = schedulesEntity.getLimits() - limitDown.getPassenger();
//                    schedulesEntity.setLimits(updatedLimits);
//                    schedulesEntities.add(schedulesEntity);
//                    log.info(String.valueOf(schedulesEntities));
//                }
//            }
//            List<SchedulesEntity> savedLimitDown = schedulesService.saveDataLimit(schedulesEntities);
//
//            log.info(String.valueOf(savedTransactions));
//            return commonResponseGenerator.succsesResponse(savedTransactions, "Sukses Menambahkan Data");
//        } catch (Exception e) {
//            log.warn(String.valueOf(e));
//            return commonResponseGenerator.failedResponse(e.getMessage());
//        }
//    }


//    @PostMapping(value = "/addTempTransaction")
//    @Operation(description = "")
//    public CommonResponse<List<TempTransactionEntity>> addTransaction(@RequestBody List<TempAddTransactionResponse> param) {
//        try {
//            List<TempTransactionEntity> transactionEntities = new ArrayList<>();
//
//            for (TempAddTransactionResponse tempAddTransaction : param) {
//                TempTransactionEntity tempTransaction = new TempTransactionEntity();
//                tempTransaction.setUuid_user(tempAddTransaction.getUuid_user());
//                tempTransaction.setUuid_schedules(tempAddTransaction.getSchedule_uid());
//                tempTransaction.setPassenger(tempAddTransaction.getPassanger());
//                transactionEntities.add(tempTransaction);
//            }
//
//            List<TempTransactionEntity> savedTransactions = tempTransactionService.addTransaction(transactionEntities);
//
//            List<SchedulesEntity> schedulesEntities = new ArrayList<>();
//            for (TempTransactionEntity limitDown : savedTransactions) {
//                SchedulesEntity schedulesEntity = new SchedulesEntity();
//                schedulesEntity.setUuid_schedules(limitDown.getUuid_schedules());
//                int updatedLimits = schedulesEntity.getLimits() - limitDown.getPassenger();
//                schedulesEntity.setLimits(updatedLimits);
//                schedulesEntities.add(schedulesEntity);
//                log.info(String.valueOf(schedulesEntities));
//            }
//            List<SchedulesEntity> savedLimitDown = schedulesService.saveDataLimit(schedulesEntities);
//
//
//            log.info(String.valueOf(savedTransactions));
//            return commonResponseGenerator.succsesResponse(savedTransactions, "Sukses Menambahkan Data");
//        } catch (Exception e) {
//            log.warn(String.valueOf(e));
//            return commonResponseGenerator.failedResponse(e.getMessage());
//        }
//    }


//    public CommonResponse<TempTransactionEntity> addTransaction(@RequestBody TempAddTransactionResponse param) {
//        try {
//            TempTransactionEntity tempTransaction = new TempTransactionEntity();
//            tempTransaction.setUuid_user(param.getUuid_user());
//            tempTransaction.setSchedule_uid(param.getSchedule_uid());
//            tempTransaction.setSeats_id(param.getSeats_id());
//            tempTransaction.setTotal_passenger(param.getTotal_passenger());
//            tempTransaction.setAirplane_name(param.getAirplane_name());
//
//            tempTransaction.setId_price(param.getId_price());
////            tempTransaction.setPrice(tempTransaction.getPrice()* tempTransaction.getTotal_passenger());
//            log.info(String.valueOf(param));
//            TempTransactionEntity transactionEntity =tempTransactionService.addTransaction(tempTransaction);
//
//            HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
//            historyTransaction.setHistory_uid(transactionEntity.getTransaction_uid());
//            log.info(String.valueOf(historyTransaction));
//            HistoryTransactionEntity saveHistory = historyTransactionService.saveDataHistory(historyTransaction);
//
//            return commonResponseGenerator.succsesResponse(saveHistory, "Sukses Menambahkan Data");
//        } catch (Exception e) {
//            log.warn(String.valueOf(e));
//            return commonResponseGenerator.failedResponse(e.getMessage());
//        }
//    }




//    @GetMapping(value = "/Search/{departure_city}/{arrival_city}/{departure_date}/{total_passanger}/{seat_type}")
//    @Operation(description = "")
//    public CommonResponse<List<TempTransactionEntity>> Search(
//            @PathVariable String departure_city, @PathVariable String arrival_city,
//            @PathVariable Date departure_date, @PathVariable int total_passanger, @PathVariable String seat_type){
//        try {
//            log.info(departure_city,arrival_city,total_passanger,seat_type,departure_date);
//            List<TempTransactionEntity> tempTransactionEntities = tempTransactionService.search(departure_city,arrival_city,departure_date,total_passanger,seat_type);
//            log.info(String.valueOf(tempTransactionEntities));
//            return commonResponseGenerator.succsesResponse(tempTransactionEntities,"Sukses Mencari Jadwal Transaction");
//        } catch (Exception e) {
//            log.warn(String.valueOf(e));
//            return commonResponseGenerator.failedResponse(e.getMessage());
//        }
//    }

//    @PutMapping(value = "/checkout")
//    @Operation(description = "Update a temporary transaction to paid status")
//    public CommonResponse<List<TempTransactionEntity>> checkoutTransaction(@RequestBody List<CheckoutTransactionResponse> param){
//
//        try {
//            TempTransactionEntity tempTransaction = new TempTransactionEntity();
//            tempTransaction.setTransaction_uid(param.getTransaction_uid());
//            tempTransaction.setTitle(param.getTitle());
//            tempTransaction.setFull_name(param.getFull_name());
//            tempTransaction.setGiven_name(param.getGiven_name());
//            tempTransaction.setBirth_date(param.getBirth_date());
//            tempTransaction.setId_card(param.getId_card());
//            TempTransactionEntity tempTransactionEntity = tempTransactionService.updateTempData(tempTransaction);
//
//            HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
//            historyTransaction.setHistory_uid(tempTransactionEntity.getTransaction_uid());
//            log.info(String.valueOf(historyTransaction));
//            HistoryTransactionEntity saveHistory = historyTransactionService.updateDataHistory(historyTransaction);
//
//            log.info(String.valueOf(tempTransaction));
//            log.info(String.valueOf(tempTransactionEntity), "Successfully updated " + param.getFull_name());
//            return commonResponseGenerator.succsesResponse(tempTransactionEntity, "Successfully updated " + param.getFull_name());
//        }catch (Exception e){
//            log.warn(String.valueOf(e));
//            return commonResponseGenerator.failedResponse(e.getMessage());
//        }
//    }

//    @PutMapping(value = "/checkout")
//    @Operation(description = "Update a temporary transaction to paid status")
//    public CommonResponse<List<TempTransactionEntity>> checkoutTransaction(@RequestBody List<CheckoutTransactionResponse> params) {
//        try {
//            List<TempTransactionEntity> updatedTransactions = new ArrayList<>();
//
//            for (CheckoutTransactionResponse param : params) {
//                TempTransactionEntity tempTransaction = new TempTransactionEntity();
//                tempTransaction.setTransaction_uid(param.getTransaction_uid());
//                tempTransaction.setTitle(param.getTitle());
//                tempTransaction.setFull_name(param.getFull_name());
//                tempTransaction.setGiven_name(param.getGiven_name());
//                tempTransaction.setBirth_date(param.getBirth_date());
//                tempTransaction.setId_card(param.getId_card());
//
//                TempTransactionEntity tempTransactionEntity = tempTransactionService.updateTempData(tempTransaction);
//                updatedTransactions.add(tempTransactionEntity);
//
//                HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
//                historyTransaction.setHistory_uid(tempTransactionEntity.getTransaction_uid());
//                // Update data history here using historyTransactionService
//                HistoryTransactionEntity saveHistory = historyTransactionService.updateDataHistory(historyTransaction);
//
//                log.info(String.valueOf(tempTransaction));
//                log.info("Successfully updated " + param.getFull_name());
//            }
//
//            return commonResponseGenerator.succsesResponse(updatedTransactions, "Successfully updated transactions");
//        } catch (Exception e) {
//            log.warn(String.valueOf(e));
//            return commonResponseGenerator.failedResponse(e.getMessage());
//        }
//    }
//
//    @PutMapping(value = "/cancelCheckout")
//    @Operation(description = "Update a temporary transaction to cancel status")
//    public CommonResponse<TempTransactionEntity> cancelCheckout(@RequestBody CancelCheckoutResponse param){
//        try {
//            TempTransactionEntity tempTransaction = new TempTransactionEntity();
//            tempTransaction.setTransaction_uid(param.getTransaction_uid());
//            TempTransactionEntity tempTransactionEntity = tempTransactionService.updateTempData(tempTransaction);
//
//            HistoryTransactionEntity historyTransaction = new HistoryTransactionEntity();
//            historyTransaction.setHistory_uid(tempTransactionEntity.getTransaction_uid());
//            log.info(String.valueOf(historyTransaction));
//            HistoryTransactionEntity saveHistory = historyTransactionService.cancelOrder(historyTransaction);
//
//            log.info(String.valueOf(tempTransaction));
//            log.info(String.valueOf(tempTransactionEntity), "Successfully updated " + param.getTransaction_uid());
//            return commonResponseGenerator.succsesResponse(tempTransactionEntity, "Successfully updated status to canceled");
//        }catch (Exception e){
//            log.warn(String.valueOf(e));
//            return commonResponseGenerator.failedResponse(e.getMessage());
//        }
//    }

//    @GetMapping(value = "/Search/{departure_city}/{arrival_city}/{departure_date}")
//    @Operation(description = "")
//    public CommonResponse<List<TempTransactionEntity>> Search(
//            @PathVariable String departure_city, @PathVariable String arrival_city, @PathVariable Date departure_date){
//        try {
//            log.info(departure_city);
//            List<TempTransactionEntity> tempTransactionEntities = tempTransactionService.search(departure_city,arrival_city, departure_date);
//            log.info(String.valueOf(tempTransactionEntities));
//            return commonResponseGenerator.succsesResponse(tempTransactionEntities,"Sukses Mencari Jadwal Transaction");
//        } catch (Exception e) {
//            log.warn(String.valueOf(e));
//            return commonResponseGenerator.failedResponse(e.getMessage());
//        }
//    }
}
