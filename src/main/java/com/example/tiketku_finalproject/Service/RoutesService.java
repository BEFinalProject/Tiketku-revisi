package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Model.RoutesEntity;
import com.example.tiketku_finalproject.Repository.RoutesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutesService {
    @Autowired
    RoutesRepository routesRepository;

    public List<RoutesEntity> findAllRoutes(){
        return routesRepository.findAll();
    }
}
