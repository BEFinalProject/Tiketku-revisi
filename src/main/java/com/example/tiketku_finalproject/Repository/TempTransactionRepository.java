package com.example.tiketku_finalproject.Repository;

import com.example.tiketku_finalproject.Model.TempTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TempTransactionRepository extends JpaRepository<TempTransactionEntity, UUID> {
}
