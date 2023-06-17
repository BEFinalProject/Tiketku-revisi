package com.example.tiketku_finalproject.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "temp_transaction")
public class TempTransactionEntity {
    @Id
    private UUID uuid_transaction;
    private UUID uuid_schedules;
    private UUID uuid_user;
    private int passenger;
    private String airplane_name;
    private String airplane_type;
    private String departure_airport;
    private String arrival_airport;
    private String departure_city;
    private String arrival_city;
    private Date departure_date;
    private Time departure_time;
    private Date arrival_date;
    private Time arrival_time;
    private int price;
    private int seats_id;
    private String seat_type;
    private String title;
    private String full_name;
    private String given_name;
    private Date birth_date;
    private String id_card;
    private Date valid_until;
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created_at;
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime modified_at;
}
