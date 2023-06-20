package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Model.AirportsEntity;
import com.example.tiketku_finalproject.Repository.AirportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AirportsService {
    @Autowired
    AirportsRepository airportsRepository;
    private LocalDateTime currentDateTime = LocalDateTime.now();

    public AirportsEntity addAirport(AirportsEntity param){
        Optional<AirportsEntity> iataCodeExists = airportsRepository.findById(param.getIata_code());
        if (iataCodeExists.isPresent()) {
            throw new RuntimeException("Iata code " + param.getIata_code() + " already exists");
        }
        param.setCreated_at(currentDateTime);
        return airportsRepository.save(param);
    }

    public AirportsEntity updateAirports(AirportsEntity param) {
        AirportsEntity updateAirport = airportsRepository.findById(param.getIata_code()).get();
        updateAirport.setAirport_name(param.getAirport_name());
        updateAirport.setCity_code(param.getCity_code());
        updateAirport.setModified_at(currentDateTime);
        return airportsRepository.save(updateAirport);
    }

    public AirportsEntity deleteAirports(String param) {
        AirportsEntity deleteAirport = airportsRepository.findById(param).get();
        airportsRepository.deleteById(param);
        return deleteAirport;
    }
}
