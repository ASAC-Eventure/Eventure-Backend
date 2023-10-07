package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AddEventEntity;
import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.models.apiEntities.Address;
import com.LTUC.Eventure.models.apiEntities.AddressCountry;
import com.LTUC.Eventure.models.apiEntities.Location;
import com.LTUC.Eventure.repositories.AddEventJPARepository;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
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
    public AppUserJPARepository appUserJPARepository;


    public AddEventController(AddEventJPARepository addEventJPARepository, AppUserJPARepository appUserJPARepository) {
        this.addEventJPARepository = addEventJPARepository;
        this.appUserJPARepository = appUserJPARepository;
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
                                     @RequestParam String time,
                                     RedirectAttributes redir){
        AddEventEntity newEvent = new AddEventEntity(name,startDate,endDate,eventUrl,location,streetAddress,price,imageUrl,false,false,time);
        newEvent.setApproved(false);
        newEvent.setBooked(false);
        newEvent.setPaymentStatus("Unpaid");
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
        String username = p.getName();
        if(username!=null){
           AppUserEntity userBooking = appUserJPARepository.findByUsername(username);
                if (event.isBooked() == false) {

                redir.addFlashAttribute("successMessageBookedEvent", "Added Successfully!");
                event.setBooked(true);
            } else {
                redir.addFlashAttribute("errorMessageBookedEvent", "Event Already Booked!");
            }

         event.setUser(userBooking);
        addEventJPARepository.save(event);
        }

        return new RedirectView("/myEvents");

    }



    @PutMapping("/approve/{id}")
    public RedirectView approveAddingNewEvent(@PathVariable("id") Long eventId, RedirectAttributes redir){
        AddEventEntity event = addEventJPARepository.findById(eventId).orElseThrow();

        if (event != null) {
            event.setApproved(true);
            redir.addFlashAttribute("successMessageBookedEvent", "Event Approved!");
        }
            addEventJPARepository.save(event);
        return new RedirectView("/adminHome");

    }
    @DeleteMapping("/decline/{id}")
    public RedirectView declineEvent(@PathVariable("id") Long id,RedirectAttributes redir) {
        addEventJPARepository.deleteById(id);
        redir.addFlashAttribute("successMessageBookedEvent", "Event Declined!");
        return new RedirectView("/adminHome");
    }

    @GetMapping("/approve/{eventId}")
    public String returnAfterApprove(){return "adminHome.html";}
}
