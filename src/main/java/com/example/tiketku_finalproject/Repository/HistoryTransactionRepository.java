package com.example.tiketku_finalproject.Repository;

import com.example.tiketku_finalproject.Model.HistoryTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryTransactionRepository extends JpaRepository<HistoryTransactionEntity, UUID> {
    @Query("SELECT h FROM HistoryTransactionEntity h WHERE h.uuid_user = :uuid_user")
    List<HistoryTransactionEntity> findByUUIDUsers(@Param("uuid_user")UUID uuid_user);

    @Query("SELECT h FROM HistoryTransactionEntity h WHERE h.uuid_user = :uuid_user AND h.uuid_history = :uuid_history")
    List<HistoryTransactionEntity> findByUUIDUserAndHistory(@Param("uuid_user") UUID uuid_user, @Param("uuid_history")UUID uuid_history);
}
