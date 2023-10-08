package com.LTUC.Eventure.models;

import com.LTUC.Eventure.models.apiEntities.Location;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @ManyToOne
    AppUserEntity user;

    public AddEventEntity(String name, LocalDate startDate, LocalDate endDate, String eventUrl, String location, String streetAddress, int price, String imageUrl, boolean isApproved, boolean isBooked) {
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
    }
    public AddEventEntity(String name, LocalDate startDate, LocalDate endDate, String eventUrl, String location, String streetAddress, int price, String imageUrl, boolean isApproved, boolean isBooked, AppUserEntity user) {
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
    }
}