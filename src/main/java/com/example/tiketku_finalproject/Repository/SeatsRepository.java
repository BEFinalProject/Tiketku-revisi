package com.example.tiketku_finalproject.Repository;

import com.example.tiketku_finalproject.Model.SeatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatsRepository extends JpaRepository<SeatsEntity, Integer> {
}
