package com.LTUC.Eventure.models.apiEntities;

import com.LTUC.Eventure.models.CommentSectionEntity;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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
    private String image;
    private boolean isAccessibleForFree;
    private final String priceCurrency = "USD";

    @OneToMany(mappedBy = "event")
    private List<CommentSectionEntity> comment;

    public Event(String type, String name, String startDate, String endDate, String url, Location location, int price, String image, boolean isAccessibleForFree, List<CommentSectionEntity> comment) {
        this.type = type;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.url = url;
        this.location = location;
        this.price = price;
        this.image = image;
        this.isAccessibleForFree = isAccessibleForFree;
        this.comment = comment;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}