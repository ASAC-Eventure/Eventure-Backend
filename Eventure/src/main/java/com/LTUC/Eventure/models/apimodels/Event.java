package com.LTUC.Eventure.models.apimodels;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
        private Location location;
            private List<Offer> offers;

        // Constructors

        public Event() {
        }

        public Event(String name, String startDate, String endDate, String url, Location location, List<Offer> offers) {
                this.name = name;
                this.startDate = startDate;
                this.endDate = endDate;
                this.url = url;
        }
// Getters and setters

}
