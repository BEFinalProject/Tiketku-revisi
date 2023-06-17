package com.example.tiketku_finalproject.Repository;

import com.example.tiketku_finalproject.Model.LimitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitsRepository extends JpaRepository<LimitsEntity, Integer> {
}
