package com.LTUC.Eventure.repositories.apiJPARepositories;

import com.LTUC.Eventure.models.apiEntities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsJPARepository extends JpaRepository<Event, Long> {
}
