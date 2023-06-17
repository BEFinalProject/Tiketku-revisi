package com.example.tiketku_finalproject.Repository;

import com.example.tiketku_finalproject.Model.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, UUID> {
    Page<UsersEntity> findAll(Pageable pageable);

    //    Optional<UsersEntity> findByUsername(String username);
    Optional<UsersEntity> findByEmail(String email);
    Optional<UsersEntity> findByPhone(String email);
}
