package com.example.tiketku_finalproject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Time;
import java.util.Date;
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
    private Date departure_date;
    private Time departure_time;
    private Date arrival_date;
    private Time arrival_time;
}
