package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminJPA extends JpaRepository<AdminUser, Long> {
}
