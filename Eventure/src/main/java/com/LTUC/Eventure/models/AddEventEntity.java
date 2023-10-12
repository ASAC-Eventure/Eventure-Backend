package com.LTUC.Eventure.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@Entity
@Table(name = "events_requested_to_add")
public class AddEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String eventUrl;

    private String location;
    private String streetAddress;

    private int price;

    private final String priceCurrency = "USD";

    private String imageUrl;

    private boolean isApproved;

    private boolean isBooked;
    private String paymentStatus;
    private String time;
    @ManyToOne
    AppUserEntity user;

    public AddEventEntity(String name, LocalDate startDate, LocalDate endDate, String eventUrl, String location, String streetAddress, int price, String imageUrl, boolean isApproved, boolean isBooked, String time) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventUrl = eventUrl;
        this.location = location;
        this.streetAddress = streetAddress;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isApproved = isApproved;
        this.isBooked = isBooked;
        this.time = time;
    }

    public AddEventEntity(String name, LocalDate startDate, LocalDate endDate, String eventUrl, String location, String streetAddress, int price, String imageUrl, boolean isApproved, boolean isBooked, AppUserEntity user, String paymentStatus, String time) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventUrl = eventUrl;
        this.location = location;
        this.streetAddress = streetAddress;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isApproved = isApproved;
        this.isBooked = isBooked;
        this.user = user;
        this.paymentStatus = paymentStatus;
        this.time = time;

    }

}