package com.LTUC.Eventure.models.apimodels;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class addressCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;  //the desired country name

    public addressCountry() {
    }



    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "addressCountry{" +
                "name='" + name + '\'' +
                '}';
    }
}
