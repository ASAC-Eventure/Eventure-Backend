package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserJPARepository extends JpaRepository<AppUserEntity, Long> {
    AppUserEntity findByUsername(String username);

    Optional<AppUserEntity> findByEmail(String email);
}

