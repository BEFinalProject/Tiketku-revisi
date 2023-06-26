package com.example.tiketku_finalproject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "routes")
public class RoutesEntity {
    @Id
    private UUID routes_uid;
    private String departure_city_code;
    private String departure_city;
    private String arrival_city_code;
    private String arrival_city;
    private String departure_airport_code;
    private String departure_airport;
    private String arrival_airport_code;
    private String arrival_airport;
    private LocalDateTime departure_time;
    private LocalDateTime arrival_time;
}
