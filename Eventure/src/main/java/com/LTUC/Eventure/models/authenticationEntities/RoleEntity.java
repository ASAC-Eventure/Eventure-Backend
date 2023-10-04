package com.LTUC.Eventure.models.authenticationEntities;


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

    @Getter
    @Column(nullable = false,name="role_name")
    @Enumerated(value = EnumType.STRING)
    private Roles title;

    public RoleEntity(long id) {
        this.id=id;
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
