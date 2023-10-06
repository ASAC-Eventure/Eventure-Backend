package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppUserJPARepository extends JpaRepository<AppUserEntity, Long> {
    AppUserEntity findByUsername(String username);
    @Query("SELECT u.username FROM AppUserEntity u WHERE u.id = :userId")
    String findNameById(@Param("userId") Long userId);

    @Query("SELECT u.id FROM AppUserEntity u WHERE u.username = :username")
    Long findIdByName(@Param("username") String username);
}

