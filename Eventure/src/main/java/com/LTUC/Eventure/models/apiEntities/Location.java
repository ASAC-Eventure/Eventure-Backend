package com.LTUC.Eventure.models.apiEntities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Data
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "venueName")
    private String name;
    @OneToOne
    private Address address;
    public Location(Address address, String name) {
        this.address = address;
        this.name=name;
    }
}