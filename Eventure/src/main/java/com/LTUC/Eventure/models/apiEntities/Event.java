package com.LTUC.Eventure.models.apiEntities;

import com.LTUC.Eventure.models.AppUserEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    private String startDate;

    private String endDate;

    private String url;

    @OneToOne
    private Location location;

    private int price;

    private final String priceCurrency = "USD";

    private String image;

    @ManyToOne
    private AppUserEntity user;

    private String paymentStatus;

    public Event(String name, String startDate, String endDate, String url, Location location, int price, String image, AppUserEntity user, String paymentStatus) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.url = url;
        this.location = location;
        this.price = price;
        this.image = image;
        this.user = user;
        this.paymentStatus = paymentStatus;
    }


}