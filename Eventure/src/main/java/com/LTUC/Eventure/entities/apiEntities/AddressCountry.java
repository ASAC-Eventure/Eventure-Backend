package com.LTUC.Eventure.entities.apiEntities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AddressCountry {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;



}
