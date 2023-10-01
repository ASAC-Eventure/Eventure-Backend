package com.LTUC.Eventure.models.apimodels;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PriceSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String price;
    private String priceCurrency;

    public PriceSpecification(String price,String priceCurrency) {
        this.price = price;
        this.priceCurrency = priceCurrency;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return priceCurrency;
    }

    public PriceSpecification() {
    }

    public String getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    @Override
    public String toString() {
        return "PriceSpecification{" +
                "price='" + price + '\'' +
                ", priceCurrency='" + priceCurrency + '\'' +
                '}';
    }
}
