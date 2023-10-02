package com.LTUC.Eventure.repositories;

import com.LTUC.Eventure.models.CommentSectionEntity;
import com.LTUC.Eventure.models.apiEntities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentSectionJPARepository extends JpaRepository<CommentSectionEntity,Long> {
    List<CommentSectionEntity> findByEvent(Event event);
}
