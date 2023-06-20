package com.arj.tiketkufinalproject.Repository;

import com.arj.tiketkufinalproject.Model.CitiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CitiesRepository extends JpaRepository<CitiesEntity, String> {
//    @Query()
}
