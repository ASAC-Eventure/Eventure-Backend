package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.apimodels.addressCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressCountryJPA extends JpaRepository<addressCountry,Long> {
}
