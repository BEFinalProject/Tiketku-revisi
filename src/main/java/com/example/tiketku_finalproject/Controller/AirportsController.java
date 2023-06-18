package com.example.tiketku_finalproject.Controller;

import com.example.tiketku_finalproject.Model.AirportsEntity;
import com.example.tiketku_finalproject.Response.CommonResponse;
import com.example.tiketku_finalproject.Response.CommonResponseGenerator;
import com.example.tiketku_finalproject.Service.AirportsService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "Airport")
@Api("Airport")
@Slf4j
public class AirportsController {
    @Autowired
    AirportsService airportsService;
    @Autowired
    CommonResponseGenerator commonResponseGenerator;

    @PostMapping("/addAirport")
    @Operation(description = "Adds a new Airport to database")
    public CommonResponse<AirportsEntity> addAirports(@RequestBody AirportsEntity param){
        try {
            AirportsEntity airportsEntity = airportsService.addAirport(param);
            log.info(String.valueOf(airportsEntity), "Successfully added " + param.getIata_code());
            return commonResponseGenerator.succsesResponse(airportsEntity, "Successfully added " + param.getIata_code());
        }catch (Exception e){
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @PutMapping("/updateAirport")
    @Operation(description = "Update Airport from database")
    public CommonResponse<AirportsEntity> updateAirport(@RequestBody AirportsEntity param){
        try {
            AirportsEntity airportsEntity = airportsService.updateAirports(param);
            log.info(String.valueOf(airportsEntity), "Successfully updated " + param.getIata_code());
            return commonResponseGenerator.succsesResponse(airportsEntity, "Successfully updated " + param.getIata_code());
        }catch (Exception e){
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @DeleteMapping("/deleteAirport/{iata_code}")
    @Operation(description = "Delete Airports from database")
    public CommonResponse<AirportsEntity> deleteAirport(@PathVariable String iata_code){
        try{
            AirportsEntity airportsEntity = airportsService.deleteAirports(iata_code);
            log.info(String.valueOf(airportsEntity), "Successfully deleted " + airportsEntity.getIata_code());
            return commonResponseGenerator.succsesResponse(airportsEntity, "Successfully deleted " + airportsEntity.getIata_code());
        }catch (EmptyResultDataAccessException e){
            log.warn(String.valueOf(e));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Iata code not found", e);
        }catch (Exception e){
            log.warn(String.valueOf(e));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete airport", e);
        }
    }
}