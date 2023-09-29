package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserJPA extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    AppUser findByEmail(String email);
}

