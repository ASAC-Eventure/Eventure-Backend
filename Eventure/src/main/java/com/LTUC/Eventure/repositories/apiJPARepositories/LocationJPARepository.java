package com.LTUC.Eventure.repositories.apiJPARepositories;

import com.LTUC.Eventure.models.apiEntities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationJPARepository extends JpaRepository<Location,Long> {
}
