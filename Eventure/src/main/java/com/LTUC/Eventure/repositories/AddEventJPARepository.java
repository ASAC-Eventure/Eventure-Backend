package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.AddEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddEventJPARepository extends JpaRepository<AddEventEntity, Long> {


    AddEventEntity findByName(String name);

    List<AddEventEntity> findAdminEventByName(String eventName);
}

