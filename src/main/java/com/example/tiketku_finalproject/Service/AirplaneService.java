package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Model.AirplanesEntity;
import com.example.tiketku_finalproject.Repository.AirplanesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AirplaneService {
    @Autowired
    AirplanesRepository airplanesRepository;

    private LocalDateTime currentDateTime = LocalDateTime.now();

    public AirplanesEntity addAirplane(AirplanesEntity param){
        Optional<AirplanesEntity> airplaneExists = airplanesRepository.findById(param.getAirplane_id());
        if (airplaneExists.isPresent()) {
            throw new RuntimeException("Airplane name " + param.getAirplane_id() + " already exists");
        }
        param.setCreated_at(currentDateTime);
        return airplanesRepository.save(param);
    }

    public AirplanesEntity updateAirplane(AirplanesEntity param) {
        AirplanesEntity updateAirplane = airplanesRepository.findById(param.getAirplane_id()).get();
        updateAirplane.setAirplane_name(param.getAirplane_name());
        updateAirplane.setAirplane_type(param.getAirplane_type());
        updateAirplane.setModified_at(currentDateTime);
        return airplanesRepository.save(updateAirplane);
    }

    public AirplanesEntity deleteAirplane(String param) {
        AirplanesEntity deleteAirplane = airplanesRepository.findById(param).get();
        airplanesRepository.deleteById(param);
        return deleteAirplane;
    }
}
