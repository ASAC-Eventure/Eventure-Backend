package com.LTUC.Eventure.repositories.apiRepositories;

import com.LTUC.Eventure.models.apimodels.Address;
import com.LTUC.Eventure.models.apimodels.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJPA extends JpaRepository<Address,Long> {
}
