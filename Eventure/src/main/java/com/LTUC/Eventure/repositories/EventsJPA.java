package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsJPA extends JpaRepository<Events, Long> {
}
