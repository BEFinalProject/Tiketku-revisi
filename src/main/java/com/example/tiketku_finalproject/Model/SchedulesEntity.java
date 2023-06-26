package com.example.tiketku_finalproject.Model;

import jakarta.persistence.*;
import lombok.Data;
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

    @ManyToOne
    @JoinColumn(name = "routes_uid", referencedColumnName = "routes_uid", insertable = false, updatable = false)
    private RoutesEntity route;
}
