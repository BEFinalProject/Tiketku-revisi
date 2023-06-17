package com.example.tiketku_finalproject.Repository;

import com.example.tiketku_finalproject.Model.AirplanesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplanesRepository extends JpaRepository<AirplanesEntity, String> {
}
