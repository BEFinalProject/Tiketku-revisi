package com.example.tiketku_finalproject.Controller;

import com.example.tiketku_finalproject.Model.AirplanesEntity;
import com.example.tiketku_finalproject.Response.CommonResponse;
import com.example.tiketku_finalproject.Response.CommonResponseGenerator;
import com.example.tiketku_finalproject.Service.AirplaneService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "Airplane")
@Api("Airplane")
@Slf4j
public class AirplanesController {
    @Autowired
    AirplaneService airplanesService;
    @Autowired
    CommonResponseGenerator commonResponseGenerator;

    @PostMapping("/addAirplane")
    @Operation(description = "Adds a new Airplane to database")
    public CommonResponse<AirplanesEntity> addAirports(@RequestBody AirplanesEntity param){
        try {
            AirplanesEntity airplaneEntity = airplanesService.addAirplane(param);
            log.info(String.valueOf(airplaneEntity), "Successfully added " + param.getAirplane_id());
            return commonResponseGenerator.succsesResponse(airplaneEntity, "Successfully added " + param.getAirplane_id());
        }catch (Exception e){
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @PutMapping("/updateAirplane")
    @Operation(description = "Update Airplane from database")
    public CommonResponse<AirplanesEntity> updateAirport(@RequestBody AirplanesEntity param){
        try {
            AirplanesEntity airplaneEntity = airplanesService.updateAirplane(param);
            log.info(String.valueOf(airplaneEntity), "Successfully updated " + param.getAirplane_id());
            return commonResponseGenerator.succsesResponse(airplaneEntity, "Successfully updated " + param.getAirplane_id());
        }catch (Exception e){
            log.warn(String.valueOf(e));
            return commonResponseGenerator.failedResponse(e.getMessage());
        }
    }

    @DeleteMapping("/deleteAirplane/{airplane_id}")
    @Operation(description = "Delete Airplane from database")
    public CommonResponse<AirplanesEntity> deleteAirport(@PathVariable String airplane_id){
        try{
            AirplanesEntity airplaneEntity = airplanesService.deleteAirplane(airplane_id);
            log.info(String.valueOf(airplaneEntity), "Successfully deleted " + airplaneEntity.getAirplane_id());
            return commonResponseGenerator.succsesResponse(airplaneEntity, "Successfully deleted " + airplaneEntity.getAirplane_id());
        }catch (EmptyResultDataAccessException e){
            log.warn(String.valueOf(e));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Airplane id not found", e);
        }catch (Exception e){
            log.warn(String.valueOf(e));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete airplane", e);
        }
    }
}
