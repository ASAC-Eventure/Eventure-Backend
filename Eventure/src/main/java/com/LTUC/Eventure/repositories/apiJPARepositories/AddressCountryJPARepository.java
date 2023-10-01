package com.LTUC.Eventure.repositories.apiRepositories;

import com.LTUC.Eventure.models.apimodels.AddressCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressCountryJPA extends JpaRepository<AddressCountry,Long> {
}
