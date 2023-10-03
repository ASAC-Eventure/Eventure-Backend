package com.LTUC.Eventure.models.apiEntities;

<<<<<<< HEAD
import javax.persistence.*;
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

=======
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
>>>>>>> origin/reneh-comment
    @Column(name = "venueName")
    private String name;
    @OneToOne
    private Address address;
<<<<<<< HEAD

    public Location() {
    }

=======
>>>>>>> origin/reneh-comment
    public Location(Address address, String name) {
        this.address = address;
        this.name=name;
    }
<<<<<<< HEAD


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
=======
>>>>>>> origin/reneh-comment
}