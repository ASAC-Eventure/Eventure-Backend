package com.LTUC.Eventure.repositories.apiJPARepositories;

import com.LTUC.Eventure.models.apiEntities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJPARepository extends JpaRepository<Address, Long> {
}
