package com.LTUC.Eventure.models.apiEntities;

<<<<<<< HEAD
import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

=======
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
>>>>>>> origin/reneh-comment
    private String streetAddress;
    private String addressLocality;
    @OneToOne
    private AddressCountry addressCountry;
<<<<<<< HEAD
    public Address() {
    }
=======
>>>>>>> origin/reneh-comment

    public Address(AddressCountry addressCountry, String streetAddress, String addressLocality) {
        this.addressCountry = addressCountry;
        this.streetAddress = streetAddress;
        this.addressLocality = addressLocality;
    }

<<<<<<< HEAD

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
=======
>>>>>>> origin/reneh-comment
}