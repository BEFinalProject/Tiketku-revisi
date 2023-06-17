package com.example.tiketku_finalproject.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "schedules")
public class SchedulesEntity {
    @Id
    private UUID uuid_schedules;
    private String airplane_id;
    private String iata_code;
    private UUID routes_uid;
    private int limits;
}
