package com.example.tiketku_finalproject.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {
    private UUID uuid_schedules;
    private String airplane_id;
    private String iata_code;
    private UUID routes_uid;
    private Integer limits;
    private String departure_city;
    private String arrival_city;
    private String departure_airport;
    private String arrival_airport;
    private LocalDateTime departure_time;
    private LocalDateTime arrival_time;
}