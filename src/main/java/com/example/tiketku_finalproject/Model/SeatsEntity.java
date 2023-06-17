package com.example.tiketku_finalproject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "seats")
public class SeatsEntity {
    @Id
    private int seats_id;
    private String seat_type;
    private int price;
}
