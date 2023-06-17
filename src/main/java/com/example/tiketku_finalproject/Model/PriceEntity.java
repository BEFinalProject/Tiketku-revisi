package com.example.tiketku_finalproject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "price")
public class PriceEntity {
    @Id
    private int id_price;
    private int price;
}
