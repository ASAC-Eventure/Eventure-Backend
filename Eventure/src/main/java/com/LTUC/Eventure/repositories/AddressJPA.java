package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.apimodels.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJPA extends JpaRepository<Address,Long> {
}
