package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.apimodels.PriceSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceSpecificationJPA extends JpaRepository<PriceSpecification,Long> {
}
