package com.LTUC.Eventure.models.apiEntities;

import com.LTUC.Eventure.models.AppUserEntity;
import com.google.gson.annotations.SerializedName;
import javax.persistence.*;
import com.google.gson.annotations.SerializedName;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @SerializedName("@type")
//    private String type;
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
//    @OneToMany(mappedBy = "event")
//    private List<CommentSectionEntity> comment;

    public Event( String name, String startDate, String endDate, String url, Location location, int price, String image, AppUserEntity user) {
//        this.type = type;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.url = url;
        this.location = location;
        this.price = price;

        this.image = image;
        this.user = user;
    }

//    public Event(String type, String name, String startDate, String endDate, String url, Location location, int price, String image, boolean isAccessibleForFree) {
//        this.type = type;
//        this.name = name;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.url = url;
//        this.location = location;
//        this.price = price;
//        this.isAccessibleForFree = isAccessibleForFree;
//        this.image = image;
//    }

}