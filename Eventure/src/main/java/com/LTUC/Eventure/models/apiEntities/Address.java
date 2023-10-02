package com.LTUC.Eventure.models.apiEntities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    Long id;
    private String streetAddress;
    private String addressLocality;
    @OneToOne
    private AddressCountry addressCountry;

    public Address(AddressCountry addressCountry, String streetAddress, String addressLocality) {
        this.addressCountry = addressCountry;
        this.streetAddress = streetAddress;
        this.addressLocality = addressLocality;
    }

}