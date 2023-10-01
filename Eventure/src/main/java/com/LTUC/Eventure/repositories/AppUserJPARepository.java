package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserJPARepository extends JpaRepository<AppUserEntity, Long> {
    AppUserEntity findByUsername(String username);

}

