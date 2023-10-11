package com.LTUC.Eventure.repositories.apiJPARepositories;

import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.models.apiEntities.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventsJPARepository extends JpaRepository<Event, Long> {
    List<Event> findByLocation_Address_AddressCountry_Name(String name);

    Events findByUser_Id(Long Id);

    List<Event> findByName(String eventName);

//    @Query("SELECT u.user FROM Event u WHERE u.id = :userId")
//    String findUserById(@Param("userId") Long userId);
}
