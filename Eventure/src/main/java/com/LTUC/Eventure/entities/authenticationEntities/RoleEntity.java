package com.LTUC.Eventure.entities.authenticationEntities;


import com.LTUC.Eventure.Enum.Roles;
import lombok.*;

import javax.persistence.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role_types")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,name="role_name")
    @Enumerated(value = EnumType.STRING)
    private Roles title;

    public Roles getTitle() {
        return title;
    }

    public void setTitle(Roles title) {
        this.title = title;
    }

    //    public RoleEntity(Roles title) {
//        this.title = title;
//    }
//    @Override
//    public Roles toString() {
//        return this.title;
//    }
}
