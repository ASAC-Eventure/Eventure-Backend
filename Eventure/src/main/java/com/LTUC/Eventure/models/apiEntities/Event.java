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
    private int price;
    private boolean isAccessibleForFree;
    private final String priceCurrency = "USD";
    private String image;

    // Constructors
    public Event() {
    }

    public Event(String type, String name, String startDate, String endDate, String url, Location location, int price, boolean isAccessibleForFree, String image) {
        this.type = type;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.url = url;
        this.location = location;
        this.price = price;
        this.isAccessibleForFree = isAccessibleForFree;
        this.image = image;

    }

    // Getters and setters


    public String getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getUrl() {
        return url;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Location getLocation() {
        return location;
    }


}