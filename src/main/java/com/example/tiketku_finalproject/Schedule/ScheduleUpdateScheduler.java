package com.example.tiketku_finalproject.Schedule;

import com.example.tiketku_finalproject.Model.SchedulesEntity;
import com.example.tiketku_finalproject.Repository.SchedulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleUpdateScheduler {

    @Autowired
    private SchedulesRepository schedulesRepository;

    @Scheduled(cron = "0 0 0 * * *") // Menjadwalkan untuk dijalankan setiap pukul 00:00:00 sore hari
    public void updateScheduleData() {
        // Lakukan operasi update sesuai kebutuhan
        // Contoh: Mengubah semua limits jadwal menjadi 30
        List<SchedulesEntity> schedules = schedulesRepository.findAll();
        for (SchedulesEntity schedule : schedules) {
            schedule.setLimits(30);
            schedulesRepository.save(schedule);
        }
    }
}

