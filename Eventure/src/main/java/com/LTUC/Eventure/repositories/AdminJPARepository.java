package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.AdminUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminJPARepository extends JpaRepository<AdminUserEntity, Long> {
    AdminUserEntity findByUsername(String username);
}
