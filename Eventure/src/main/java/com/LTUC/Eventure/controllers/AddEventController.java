package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AddEventEntity;
import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.repositories.AddEventJPARepository;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;

@Controller
public class AddEventController {

    public AddEventJPARepository addEventJPARepository;
    public AppUserJPARepository appUserJPARepository;


    public AddEventController(AddEventJPARepository addEventJPARepository, AppUserJPARepository appUserJPARepository) {
        this.addEventJPARepository = addEventJPARepository;
        this.appUserJPARepository = appUserJPARepository;
    }

    @GetMapping("/contact")
    public String addEventData(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (username.equals("anonymousUser")) {
            m.addAttribute("isUsernameFound", "no");
        } else {
            m.addAttribute("isUsernameFound", "yes");
        }
        return "contact.html";
    }

    @PostMapping("/addNewEvent")
    public RedirectView addEventData(@RequestParam String name,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                     @RequestParam String eventUrl,
                                     @RequestParam String imageUrl,
                                     @RequestParam String location,
                                     @RequestParam String streetAddress,
                                     @RequestParam int price,
                                     @RequestParam String time,
                                     RedirectAttributes redir) {
        LocalDate currentDate = LocalDate.now();

        if (endDate.isBefore(startDate)) {
            redir.addFlashAttribute("errorMessageEndBeforeStart", "End date cannot be before the start date.");
            return new RedirectView("/contact");
        }

        if (startDate.isBefore(currentDate)) {
            redir.addFlashAttribute("errorMessageStartDate", "Date cannot be in the past.");
            return new RedirectView("/contact");
        }

        if (endDate.isBefore(currentDate)) {
            redir.addFlashAttribute("errorMessageEndDate", "Date cannot be in the past.");
            return new RedirectView("/contact");
        }

        AddEventEntity newEvent = new AddEventEntity(name, startDate, endDate, eventUrl, location, streetAddress, price, imageUrl, false, false, time);
        AddEventEntity existingEvent = addEventJPARepository.findByName(name);

        if (existingEvent == null) {
            redir.addFlashAttribute("successMessageBookedEvent", "Your event has been submitted and is awaiting admin approval. Thank you for sharing!");
            addEventJPARepository.save(newEvent);
        } else {
            redir.addFlashAttribute("errorMessageBookedEvent", "Event Already Saved!");
        }

        return new RedirectView("/contact");
    }

    @PostMapping("/bookCreatedEvent")
    public RedirectView BookingAddedNewEvent(Principal p, RedirectAttributes redir,
                                             @RequestParam String name,
                                             @RequestParam String startDate,
                                             @RequestParam String endDate,
                                             @RequestParam String url,
                                             @RequestParam String image,
                                             @RequestParam String location,
                                             @RequestParam String address,
                                             @RequestParam String price,
                                             @RequestParam String time
    ) {
        System.out.println("reached booking created event");
        String username = p.getName();
        if (username != null) {
            AppUserEntity userBooking = appUserJPARepository.findByUsername(username);

            AddEventEntity newEvent = new AddEventEntity(name, LocalDate.parse(startDate), LocalDate.parse(endDate), url, location, address, Integer.valueOf(price), image, true, true, userBooking, "Unpaid", time);

            if (!userBooking.getNewEvents().stream().anyMatch(e -> e.getName().equals(newEvent.getName()))) {
                redir.addFlashAttribute("successMessageBookedEvent", "Added Successfully!");
                addEventJPARepository.save(newEvent);
            } else {
                redir.addFlashAttribute("errorMessageBookedEvent", "Event Already Booked!");
            }

        }
        return new RedirectView("/myEvents");
    }


    @PostMapping("/paymentCreated")
    public RedirectView paymentMethod_forCreated(@RequestParam(name = "eventId") Long eventId) {
        AddEventEntity event = addEventJPARepository.findById(eventId).orElse(null);
        if (event != null && "Unpaid".equals(event.getPaymentStatus())) {
            event.setPaymentStatus("Pending");
            addEventJPARepository.save(event);
        }
        return new RedirectView("/myEvents");
    }

    @DeleteMapping("/unbook-eventCreated/{id}")
    public RedirectView deleteEventCreatedById(@PathVariable Long id) {
        addEventJPARepository.deleteById(id);
        return new RedirectView("/myEvents");
    }

    @PutMapping("/approve/{id}")
    public RedirectView approveAddingNewEvent(@PathVariable("id") Long eventId, RedirectAttributes redir) {
        AddEventEntity event = addEventJPARepository.findById(eventId).orElseThrow();

        if (event != null) {
            event.setApproved(true);
            redir.addFlashAttribute("successMessageBookedEvent", "Event Approved!");
        }
        addEventJPARepository.save(event);
        return new RedirectView("/requested-events");

    }

    @DeleteMapping("/decline/{id}")
    public RedirectView declineEvent(@PathVariable("id") Long id, RedirectAttributes redir) {
        addEventJPARepository.deleteById(id);
        redir.addFlashAttribute("successMessageBookedEvent", "Event Declined!");
        return new RedirectView("/requested-events");
    }

    @GetMapping("/approve/{eventId}")
    public String returnAfterApprove() {
        return "adminHome.html";
    }

}
