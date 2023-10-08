package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.AddEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddEventJPARepository extends JpaRepository<AddEventEntity,Long> {

    List<AddEventEntity> findAllByName(String name);

    AddEventEntity findByName(String name);
}
