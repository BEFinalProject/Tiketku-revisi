package com.example.tiketku_finalproject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "limits")
public class LimitsEntity {
    @Id
    private int id_limit;
    private int limit;
}
