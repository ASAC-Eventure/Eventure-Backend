package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.apimodels.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheEventJPA extends JpaRepository<Event,Long> {
    List<Event> findByLocation_Address_AddressCountry_Name(String name);

}
