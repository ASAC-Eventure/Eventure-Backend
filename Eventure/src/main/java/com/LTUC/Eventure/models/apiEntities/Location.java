package com.LTUC.Eventure.models.apiEntities;

import javax.persistence.*;
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "venueName")
    private String name;
    @OneToOne
    private Address address;

    public Location() {
    }

    public Location(Address address, String name) {
        this.address = address;
        this.name=name;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "address=" + address +
                '}';
    }
}