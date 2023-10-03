package com.LTUC.Eventure.models.apiEntities;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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