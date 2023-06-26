package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Model.*;
import com.example.tiketku_finalproject.Repository.*;
import com.example.tiketku_finalproject.Response.ScheduleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SchedulesService {
    @Autowired
    SchedulesRepository schedulesRepository;

    public List<SchedulesEntity> findAllSchedulesData() {
        return schedulesRepository.findAll();
    }

    public List<SchedulesEntity> searchByUuidSchedules(UUID uuid_schedules){
        return schedulesRepository.searchByUUIDSchedules(uuid_schedules);
    }
    public List<SchedulesEntity> saveDataLimit(List<SchedulesEntity> schedulesEntities) {

        List<SchedulesEntity> savedSchedules = new ArrayList<>();
        for (SchedulesEntity schedulesEntity : schedulesEntities) {
            Optional<SchedulesEntity> optionalSavedSchedule = schedulesRepository.findById(schedulesEntity.getUuid_schedules());
            if (optionalSavedSchedule.isPresent()) {
                SchedulesEntity savedSchedule = optionalSavedSchedule.get();
                savedSchedule.setUuid_schedules(schedulesEntity.getUuid_schedules());
                savedSchedule.setAirplane_id(schedulesEntity.getAirplane_id());
                savedSchedule.setIata_code(schedulesEntity.getIata_code());
                savedSchedule.setRoutes_uid(schedulesEntity.getRoutes_uid());
                savedSchedule.setLimits(schedulesEntity.getLimits());
                savedSchedules.add(savedSchedule);
            }
        }
        return schedulesRepository.saveAll(savedSchedules);
    }

    public Optional<SchedulesEntity> getByUuidSchedules(UUID uuidSchedules) {
        return schedulesRepository.findById(uuidSchedules);
    }

    /*public List<SchedulesEntity> searchTiket(String departure_city, String arrival_city, Date departure_date, Integer total_passenger) {
        List<Object[]> results = schedulesRepository.searching(departure_city, arrival_city, departure_date, total_passenger);
        List<SchedulesEntity> schedules = new ArrayList<>();

        for (Object[] result : results) {
            SchedulesEntity schedule = new SchedulesEntity();
            schedule.setUuid_schedules((UUID) result[0]);
            schedule.setAirplane_id((String) result[1]);
            schedule.setIata_code((String) result[2]);
            schedule.setRoutes_uid((UUID) result[3]);
            schedule.setLimits((Integer) result[4]);
            schedules.add(schedule);
        }

        return schedules;
    }*/

    public List<ScheduleResponse> searchTiket(String departure_city, String arrival_city, LocalDateTime departure_time, Integer total_passenger) {
        List<Object[]> results = schedulesRepository.searching(departure_city, arrival_city, departure_time, total_passenger);
        List<ScheduleResponse> schedules = new ArrayList<>();

        for (Object[] result : results) {
            ScheduleResponse schedule = new ScheduleResponse();
            schedule.setUuid_schedules((UUID) result[0]);
            schedule.setAirplane_id((String) result[1]) ;
            schedule.setIata_code((String) result[2]);
            schedule.setRoutes_uid((UUID) result[3]);
            schedule.setLimits((Integer) result[4]);
            schedule.setDeparture_city((String) result[5]);
            schedule.setArrival_city((String) result[6]);
            schedule.setDeparture_airport((String) result[7]);
            schedule.setArrival_airport((String) result[8]);
//            schedule.setDeparture_date((Date) result[9]);
//            schedule.setArrival_date((Date) result[10]);
            schedule.setDeparture_time((LocalDateTime) result[9]);
            schedule.setArrival_time((LocalDateTime) result[10]);
            schedules.add(schedule);
        }

        return schedules;
    }


}
