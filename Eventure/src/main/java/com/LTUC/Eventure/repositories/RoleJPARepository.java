package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.authenticationEntities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleJPARepository extends JpaRepository<RoleEntity, Long> {

    @Query(value = "SELECT * FROM role_types r WHERE r.role_name = :title", nativeQuery = true)
    Optional<RoleEntity> findRoleEntityByTitle(String title);
}
