package com.LTUC.Eventure.models.apiEntities;

import com.google.gson.annotations.SerializedName;

import javax.persistence.*;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @SerializedName("@type")

    private String type;
    private String name;
    private String startDate;
    private String endDate;
    private String url;
    @OneToOne
    private Location location;
    private double price;
    private boolean isAccessibleForFree;
    private final String priceCurrency = "USD";

    // Constructors
    public Event() {
    }


    public Event(String type, String name, String startDate, String endDate, String url, Location location, double price, boolean isAccessibleForFree) {
        this.type = type;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.url = url;
        this.location = location;
        this.price = price;
        this.isAccessibleForFree = isAccessibleForFree;

    }

    // Getters and setters


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", url='" + url + '\'' +
                ", location=" + location +
                '}';
    }
}