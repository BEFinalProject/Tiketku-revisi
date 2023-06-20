package com.arj.tiketkufinalproject.Service;

import com.arj.tiketkufinalproject.Model.SeatsEntity;
import com.arj.tiketkufinalproject.Repository.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatsService {
    @Autowired
    SeatsRepository seatsRepository;
    /*List<SeatsEntity> findAllSeatsByAirplaneName(){

    }*/
}
