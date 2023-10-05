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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;
import java.util.List;


@Controller
public class SaveEventsController {
    private AppUserJPARepository userJPARepo;
    private EventsJPARepository eventsJPARepo;
    private AddressCountryJPARepository addressCountryJPARepository;
    private LocationJPARepository locationJPARepository;
    private AddressJPARepository addressJPARepository;

    @Autowired
    public SaveEventsController(AppUserJPARepository userJPARepo, EventsJPARepository eventsJPARepo, AddressCountryJPARepository addressCountryJPARepository, LocationJPARepository locationJPARepository, AddressJPARepository addressJPARepository) {
        this.userJPARepo = userJPARepo;
        this.eventsJPARepo = eventsJPARepo;
        this.addressCountryJPARepository = addressCountryJPARepository;
        this.locationJPARepository = locationJPARepository;
        this.addressJPARepository = addressJPARepository;
    }

    @GetMapping("/myEvents")
    public String userPage(Model model, Principal p) {
        String username = p.getName();
        if (username != null) {
            AppUserEntity user = userJPARepo.findByUsername(username);
            List<Event> userEvents = user.getBookedEvents();
//            System.out.println(userEvents);
            model.addAttribute("userEvents", userEvents);
        }
        return "user-events.html";
    }

    @PostMapping("/book-event")
    public RedirectView bookEvent(Principal p, RedirectAttributes redir,
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


            Event event = new Event(eventName, eventStartDate, eventEndDate, eventUrl, location, (int) (50 + (Math.random() * (250 - 50))), image, user);
            Event bookedEvent = eventsJPARepo.findByName(eventName);
            if (bookedEvent == null) {
                eventsJPARepo.save(event);
                redir.addFlashAttribute("successMessageBookedEvent", "Added Successfully!");
            } else if (!user.getBookedEvents().stream().anyMatch(e -> e.getName().equals(eventName))) {
                eventsJPARepo.save(event);
                redir.addFlashAttribute("successMessageBookedEvent", "Added Successfully!");

            } else {
                redir.addFlashAttribute("errorMessageBookedEvent", "Event Already Booked!");
            }
        }
        return new RedirectView("/myEvents");
    }

    @DeleteMapping("/unbook-event/{id}")
    public RedirectView deleteEventById(@PathVariable Long id) {
        eventsJPARepo.deleteById(id);
        return new RedirectView("/myEvents");
    }


}
