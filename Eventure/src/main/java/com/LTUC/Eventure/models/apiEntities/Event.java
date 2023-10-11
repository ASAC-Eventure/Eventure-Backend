package com.LTUC.Eventure.models.apiEntities;

import com.LTUC.Eventure.models.AppUserEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;


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
        this.paymentStatus=paymentStatus;
    }

//    public Event(String eventName, String eventStartDate, String eventEndDate, String eventUrl, Location location, int i, String image, AppUserEntity user, String unpaid) {
//    }

//    @Override
//    public String toString() {
//        return "Event{" +
//                "name='" + name + '\'' +
//                ", startDate='" + startDate + '\'' +
//                ", endDate='" + endDate + '\'' +
//                ", url='" + url + '\'' +
//                ", location=" + location +
//                ", price=" + price +
//                ", priceCurrency='" + priceCurrency + '\'' +
//                ", image='" + image + '\'' +
//                ", user=" + user +
//                ", paymentStatus='" + paymentStatus + '\'' +
//                '}';
//    }
}