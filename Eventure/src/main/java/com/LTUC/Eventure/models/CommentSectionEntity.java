package com.LTUC.Eventure.models;

import com.LTUC.Eventure.models.apiEntities.Event;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class CommentSectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String username;
    private String comment;
    private LocalDate localDate;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUserEntity user;

    public CommentSectionEntity(String username, String comment, LocalDate localDate, Event event,AppUserEntity user) {
        this.username = username;
        this.comment = comment;
        this.localDate = localDate;
        this.event = event;
        this.user = user;
    }
}
