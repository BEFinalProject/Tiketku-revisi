package com.example.tiketku_finalproject.Repository;

import com.example.tiketku_finalproject.Model.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Integer> {
}
