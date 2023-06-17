package com.example.tiketku_finalproject.Repository;

import com.example.tiketku_finalproject.Model.AirportsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportsRepository extends JpaRepository<AirportsEntity, String> {
}
