package com.LTUC.Eventure.models.apimodels;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String addressLocality;
    private  String streetAddress;

    @OneToOne
    private addressCountry addressCountry;

    public Address() {
    }

    public Address(String addressLocality, String streetAddress, com.LTUC.Eventure.models.apimodels.addressCountry addressCountry) {
        this.addressLocality = addressLocality;
        this.streetAddress = streetAddress;
        this.addressCountry = addressCountry;
    }




    public String getAddressLocality() {
        return addressLocality;
    }

    public String getStreetAddress() {
        return streetAddress;
    }
    public addressCountry getAddressCountry() {
        return addressCountry;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressLocality='" + addressLocality + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", addressCountry=" + addressCountry +
                '}';
    }
}
