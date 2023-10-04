package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.models.apiEntities.*;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.AddressCountryJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.AddressJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.EventsJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.LocationJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
public class SaveEventsController {
    private AppUserJPARepository userJPARepo;
    private EventsJPARepository eventsJPARepo;
    private AddressCountryJPARepository addressCountryJPARepository;
    private LocationJPARepository   locationJPARepository;
    private AddressJPARepository    addressJPARepository;
    @Autowired
    public SaveEventsController(AppUserJPARepository userJPARepo, EventsJPARepository eventsJPARepo, AddressCountryJPARepository addressCountryJPARepository, LocationJPARepository locationJPARepository, AddressJPARepository addressJPARepository) {
        this.userJPARepo = userJPARepo;
        this.eventsJPARepo = eventsJPARepo;
        this.addressCountryJPARepository = addressCountryJPARepository;
        this.locationJPARepository = locationJPARepository;
        this.addressJPARepository = addressJPARepository;
    }

    @GetMapping("/myevents")
    public String userPage(Model model, Principal p) {
        String username = p.getName();
        if (username != null) {
            AppUserEntity user = userJPARepo.findByUsername(username);

            // Assuming you have a method in userJPARepo to fetch events for a user
            List<Event> userEvents = user.getBookedEvents();
            System.out.println(userEvents);
            model.addAttribute("userEvents", userEvents);
        }
        return "user-events.html";
    }

@PostMapping("/book-event")
public RedirectView bookEvent(Principal p,
                              @RequestParam String eventName,
                              @RequestParam String eventStartDate,
                              @RequestParam String eventEndDate,
                              @RequestParam String eventUrl,
                              @RequestParam String image,
                              @RequestParam String eventLocationName,
                              @RequestParam String eventAddressStreet,
                              @RequestParam String eventAddressLocality,
                              @RequestParam String eventAddressCountryName) {
    System.out.println("Reached book-event function");

    String username = p.getName();
    if (username != null) {
        AppUserEntity user = userJPARepo.findByUsername(username);

        // Check if the Location already exists in the database or create a new one
       // Location location = locationJPARepository.f(eventLocationName);
//        if (location == null) {
           Location location = new Location();
            location.setName(eventLocationName);

            Address address = new Address();
            address.setStreetAddress(eventAddressStreet);
            address.setAddressLocality(eventAddressLocality);

            AddressCountry addressCountry = new AddressCountry();
            addressCountry.setName(eventAddressCountryName);

            address.setAddressCountry(addressCountry);
            location.setAddress(address);

        addressCountryJPARepository.save(addressCountry);
        addressJPARepository.save(address);
            // Save the Location to the database
            locationJPARepository.save(location);
        //}

        Event event = new Event(eventName, eventStartDate, eventEndDate, eventUrl, location, (int) (50 + (Math.random() * (250 - 50))), image, user);

        eventsJPARepo.save(event);
    }
    return new RedirectView("/myevents");
}

}
