package com.LTUC.Eventure.models.apimodels;

import javax.persistence.*;
import java.util.List;
@Entity
public class Event {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String startDate;
        private String endDate;
        private String url;
        private String image;
        @OneToOne
        private Location location;
//@OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
////        @OneToOne
//private List<Offer> offers;

        // Constructors

        public Event() {
        }

        public Event(String name, String startDate, String endDate, String url, String image, Location location) {
                this.name = name;
                this.startDate = startDate;
                this.endDate = endDate;
                this.url = url;
                this.image = image;
                this.location = location;
        }

        // Getters and setters

//        public List<Offer> getOffers() {
//                return offers;
//        }

        public Location getLocation() {
                return location;
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

        public String getImage() {
                return image;
        }

        @Override
        public String toString() {
                return "Event{" +
                        "name='" + name + '\'' +
                        ", startDate='" + startDate + '\'' +
                        ", endDate='" + endDate + '\'' +
                        ", url='" + url + '\'' +
                        ", image='" + image + '\'' +
                        ", location=" + location +
                        '}';
        }
}
