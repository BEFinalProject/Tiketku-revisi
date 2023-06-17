package com.example.tiketku_finalproject.Repository;

import com.example.tiketku_finalproject.Model.HistoryTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoryTransactionRepository extends JpaRepository<HistoryTransactionEntity, UUID> {
}
