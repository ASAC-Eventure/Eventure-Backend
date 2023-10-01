package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.apimodels.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferJPA extends JpaRepository<Offer,Long> {
}
