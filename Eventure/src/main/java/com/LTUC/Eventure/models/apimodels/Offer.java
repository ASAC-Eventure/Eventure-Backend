package com.LTUC.Eventure.models.apimodels;

import javax.persistence.*;

@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @OneToOne
//    private PriceSpecification priceSpecification;

    private String url;
//@ManyToOne
//    private Event event;
    public Offer() {
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public void setEvent(Event event) {
//        this.event = event;
//    }

//    public void setPriceSpecification(PriceSpecification priceSpecification) {
//        this.priceSpecification = priceSpecification;
//    }

    public void setUrl(String url) {
        this.url = url;
    }

//    public Offer(PriceSpecification priceSpecification, String url, Event event) {
//        this.priceSpecification = priceSpecification;
//        this.url = url;
//        this.event=event;
//    }

    public Long getId() {
        return id;
    }

//    public Event getEvent() {
//        return event;
//    }
//    public Event getEvent() {
//        return event;
//    }

//    public PriceSpecification getPriceSpecification() {
//        return priceSpecification;
//    }

    public String getUrl() {
        return url;
    }
}
