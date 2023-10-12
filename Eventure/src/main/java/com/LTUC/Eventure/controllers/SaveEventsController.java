package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AddEventEntity;
import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.models.apiEntities.Address;
import com.LTUC.Eventure.models.apiEntities.AddressCountry;
import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.models.apiEntities.Location;
import com.LTUC.Eventure.repositories.AddEventJPARepository;
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
    private AddEventJPARepository addEventJPARepository;

    @Autowired
    public SaveEventsController(AppUserJPARepository userJPARepo, EventsJPARepository eventsJPARepo, AddressCountryJPARepository addressCountryJPARepository, LocationJPARepository locationJPARepository, AddressJPARepository addressJPARepository, AddEventJPARepository addEventJPARepository) {
        this.userJPARepo = userJPARepo;
        this.eventsJPARepo = eventsJPARepo;
        this.addressCountryJPARepository = addressCountryJPARepository;
        this.locationJPARepository = locationJPARepository;
        this.addressJPARepository = addressJPARepository;
        this.addEventJPARepository = addEventJPARepository;
    }

    @GetMapping("/myEvents")
    public String userPage(Model model, Principal p) {
        String username = p.getName();
        if (username != null) {
            AppUserEntity user = userJPARepo.findByUsername(username);
            List<Event> userEvents = user.getBookedEvents();
            List<AddEventEntity> addedEvents = user.getNewEvents();
            model.addAttribute("userEvents", userEvents);
            model.addAttribute("addedEvents", addedEvents);
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
                                  @RequestParam String price,
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


            Event event = new Event(eventName, eventStartDate, eventEndDate, eventUrl, location,Integer.valueOf(price), image, user, "Unpaid");
            if (!user.getBookedEvents().stream().anyMatch(e -> e.getName().equals(eventName))) {
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

    @PostMapping("/payment")
    public RedirectView paymentMethod(@RequestParam(name = "eventId") Long eventId) {
        Event event = eventsJPARepo.findById(eventId).orElse(null);
        if (event != null && "Unpaid".equals(event.getPaymentStatus())) {
            event.setPaymentStatus("Pending");
            eventsJPARepo.save(event);
        }
        return new RedirectView("/myEvents");
    }
}
