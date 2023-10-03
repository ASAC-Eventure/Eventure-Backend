package com.LTUC.Eventure.models.apiEntities;

import lombok.*;

import javax.persistence.*;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String streetAddress;
    private String addressLocality;
    @OneToOne
    private AddressCountry addressCountry;

}