package com.example.tiketku_finalproject.Repository;

import com.example.tiketku_finalproject.Model.CitiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepository extends JpaRepository<CitiesEntity, String> {
}
