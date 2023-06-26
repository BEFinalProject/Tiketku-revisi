package com.example.tiketku_finalproject.Repository;

import com.example.tiketku_finalproject.Model.HistoryTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryTransactionRepository extends JpaRepository<HistoryTransactionEntity, UUID> {
    @Query("SELECT h FROM HistoryTransactionEntity h WHERE h.uuid_user = :uuid_user")
    List<HistoryTransactionEntity> findByUUIDUsers(@Param("uuid_user")UUID uuid_user);
    @Query("SELECT h FROM HistoryTransactionEntity h WHERE h.departure_time = :departure_time AND h.uuid_user = :uuid_user")
    List<HistoryTransactionEntity> findByDepartureDate(@Param("departure_time") LocalDateTime departure_time, @Param("uuid_user")UUID uuid_user);
    @Query("SELECT h FROM HistoryTransactionEntity h WHERE h.uuid_user = :uuid_user AND h.uuid_history = :uuid_history")
    List<HistoryTransactionEntity> findByUUIDUserAndHistory(@Param("uuid_user") UUID uuid_user, @Param("uuid_history")UUID uuid_history);
    @Query("SELECT h FROM HistoryTransactionEntity h WHERE h.uuid_history =:uuid_history")
    List<HistoryTransactionEntity> findByUUIDHistory(@Param("uuid_history") UUID uuid_history);

    @Query("SELECT SUM(h.price) AS total_price, COUNT(*) AS total_passanger FROM HistoryTransactionEntity h WHERE h.uuid_user = :uuid_users and h.created_at = :created_at and h.status = 'Unpaid'")
    List<Object[]> findTotalPriceAndPassangerByUUIDUserAndCreatedAt(@Param("uuid_users") UUID uuid_users, @Param("created_at") LocalDateTime created_at);
}
