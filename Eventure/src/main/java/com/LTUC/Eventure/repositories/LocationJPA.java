package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.apimodels.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationJPA extends JpaRepository<Location,Long> {
}
