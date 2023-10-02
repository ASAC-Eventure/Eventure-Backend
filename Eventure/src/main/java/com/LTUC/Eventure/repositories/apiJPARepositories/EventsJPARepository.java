package com.LTUC.Eventure.repositories.apiJPARepositories;

import com.LTUC.Eventure.models.apiEntities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventsJPARepository extends JpaRepository<Event, Long> {
    List<Event> findByLocation_Address_AddressCountry_Name(String name);
}
