package com.LTUC.Eventure.models.apiEntities;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String streetAddress;
    private String addressLocality;
    @OneToOne
    private AddressCountry addressCountry;
    public Address() {
    }

    public Address(AddressCountry addressCountry, String streetAddress, String addressLocality) {
        this.addressCountry = addressCountry;
        this.streetAddress = streetAddress;
        this.addressLocality = addressLocality;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressCountry getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(AddressCountry addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getAddressLocality() {
        return addressLocality;
    }

    public void setAddressLocality(String addressLocality) {
        this.addressLocality = addressLocality;
    }
}