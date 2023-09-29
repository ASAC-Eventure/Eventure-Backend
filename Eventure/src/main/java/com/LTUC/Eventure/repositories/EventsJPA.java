package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.apimodels.Event;
import com.LTUC.Eventure.models.apimodels.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsJPA extends JpaRepository<Event, Long> {
}
