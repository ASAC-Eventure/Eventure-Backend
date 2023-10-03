package com.LTUC.Eventure.entities.apiEntities;

import lombok.*;

import javax.persistence.*;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "venueName")
    private String name;
    @OneToOne
    private Address address;

    @Override
    public String toString() {
        return "Location{" +
                "address=" + address +
                '}';
    }
}