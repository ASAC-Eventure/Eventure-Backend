package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AddEventEntity;
import com.LTUC.Eventure.models.apiEntities.Address;
import com.LTUC.Eventure.models.apiEntities.AddressCountry;
import com.LTUC.Eventure.models.apiEntities.Location;
import com.LTUC.Eventure.repositories.AddEventJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.AddressCountryJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.AddressJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.LocationJPARepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AddEventController {

    public AddEventJPARepository addEventJPARepository;

    public LocationJPARepository locationJPARepository;
    public AddressJPARepository addressJPARepository;
    public AddressCountryJPARepository addressCountryJPARepository;


    public AddEventController(AddEventJPARepository addEventJPARepository) {
        this.addEventJPARepository = addEventJPARepository;
    }




    @PostMapping("/addNewEvent")
    public RedirectView addEventData(@RequestParam String name,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate endDate,
                                     @RequestParam String eventUrl,
                                     @RequestParam String imageUrl,
                                     @RequestParam String location,
                                     @RequestParam String streetAddress,
                                     @RequestParam int price,
                                     RedirectAttributes redir){
        AddEventEntity newEvent = new AddEventEntity(name,startDate,endDate,eventUrl,location,streetAddress,price,imageUrl,false,false);
      newEvent.setApproved(false);
      newEvent.setBooked(false);
        AddEventEntity existingEvent = addEventJPARepository.findByName(name);

        if (existingEvent == null) {
            redir.addFlashAttribute("successMessageBookedEvent", "Added Successfully!");
            addEventJPARepository.save(newEvent);
        } else {
            redir.addFlashAttribute("errorMessageBookedEvent", "Event Already Saved!");

        }
        return new RedirectView("/aboutUs") ;

    }

    @PutMapping("/book/{id}")
    public RedirectView BookingAddedNewEvent(@PathVariable("id") Long eventId, Principal p, RedirectAttributes redir){
        AddEventEntity event = addEventJPARepository.findById(eventId).orElseThrow();

          if(p!=null) {
                if (event.isBooked() == false) {

                redir.addFlashAttribute("successMessageBookedEvent", "Added Successfully!");
                event.setBooked(true);
            } else {
                redir.addFlashAttribute("errorMessageBookedEvent", "Event Already Booked!");

            }
        }

        addEventJPARepository.save(event);


        return new RedirectView("/myEvents");

    }



    @PutMapping("/approve/{id}")
    public RedirectView approveAddingNewEvent(@PathVariable("id") Long eventId){
        AddEventEntity event = addEventJPARepository.findById(eventId).orElseThrow();

        if (event != null) {
            event.setApproved(true);

        }
            addEventJPARepository.save(event);
        return new RedirectView("/adminHome");

    }
    @DeleteMapping("/decline/{id}")
    public RedirectView declineEvent(@PathVariable("id") Long id) {
        addEventJPARepository.deleteById(id);
        return new RedirectView("/adminHome");
    }

    @GetMapping("/approve/{eventId}")
    public String returnAfterApprove(){return "adminHome.html";}
}
