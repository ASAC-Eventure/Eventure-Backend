package com.LTUC.Eventure.models.apiEntities;

<<<<<<< HEAD
import com.google.gson.annotations.SerializedName;

import javax.persistence.*;

@Entity
=======
import com.LTUC.Eventure.models.CommentSectionEntity;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
>>>>>>> origin/reneh-comment
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @SerializedName("@type")
<<<<<<< HEAD

=======
>>>>>>> origin/reneh-comment
    private String type;
    private String name;
    private String startDate;
    private String endDate;
    private String url;
    @OneToOne
    private Location location;
    private int price;
<<<<<<< HEAD
    private boolean isAccessibleForFree;
    private final String priceCurrency = "USD";
    private String image;

    // Constructors
    public Event() {
    }

    public Event(String type, String name, String startDate, String endDate, String url, Location location, int price, boolean isAccessibleForFree, String image) {
=======
    private String image;
    private boolean isAccessibleForFree;
    private final String priceCurrency = "USD";

    @OneToMany(mappedBy = "event")
    private List<CommentSectionEntity> comment;

    public Event(String type, String name, String startDate, String endDate, String url, Location location, int price, String image, boolean isAccessibleForFree, List<CommentSectionEntity> comment) {
>>>>>>> origin/reneh-comment
        this.type = type;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.url = url;
        this.location = location;
        this.price = price;
<<<<<<< HEAD
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
=======
        this.image = image;
        this.isAccessibleForFree = isAccessibleForFree;
        this.comment = comment;
>>>>>>> origin/reneh-comment
    }

    public void setPrice(int price) {
        this.price = price;
    }
<<<<<<< HEAD

    public Location getLocation() {
        return location;
    }


=======
>>>>>>> origin/reneh-comment
}