package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.EventsJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


@Controller
public class  AdminController {

    @Autowired
    AppUserJPARepository appUserJPARepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EventsJPARepository eventsJPARepository;

    HttpServletRequest request;

    @GetMapping("/adminHome")
    public String retrieveAdminHome() {
        return "admin-home.html";
    }

    public void authWithHttpServletRequest(String adminUsername, String adminPassword) {
        try {
            request.login(adminUsername, adminPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/booked-admin")
    public String getAllBookedEvents(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        if (allBookedEvents == null) {
            System.out.println("No booked events");
            return "admin-home.html";
        }
        m.addAttribute("bookedEvents", allBookedEvents);
        return "admin-home.html";

    }

    @PostMapping("/update-event-status/{eventId}")
    public RedirectView updateStatus_BookedEvents_toPaid(@PathVariable Long eventId) {
        try {
            Event eventRetrieved = null;
            Optional<Event> event = eventsJPARepository.findById(eventId);
            if (event.isPresent()) {
                eventRetrieved = event.get();
                // eventRetrieved.setStatus("Paid");
                eventsJPARepository.save(eventRetrieved);
            } else {
                return new RedirectView("/adminHome");
            }
        } catch (Exception e) {
            System.out.println("Error in changing event to paid" + e);
            return new RedirectView("/adminHome");
        }
        return new RedirectView("/booked-admin");

    }

//    public void updateStatus_BookedEvents_toCancelled() {
//        List<Event> allBookedEvents = eventsJPARepository.findAll();
//        if (!allBookedEvents.isEmpty()) {
//
//            LocalDate currentDate = LocalDate.now();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            List<Event> pendingEvents=allBookedEvents.stream().filter(s->s.getStatus=="pending").collect(toList());
//            //List<Event> deadlinePaymentExceeded=pendingEvents.stream().filter(LocalDate.parse(s->s.getStartDate,formatter).compareTo(currentDate)>0).collect(toList());
////       for(Event e:deadlinePaymentExceeded ){
////           // call teh function that cancel them or just send them to the admin
////       }
//
//
//        }
//    }

//        @GetMapping("/most-booked")
//        public String getMostBooked(Model m) {
//            List<Event> allBookedEvents = eventsJPARepository.findAll();
//            if (allBookedEvents == null) {
//                System.out.println("No booked events");
//                return "admin-home.html";
//            }
//            Map<String, Long> nameCountMap = allBookedEvents.stream()
//                    .collect(groupingBy(s -> s.getName(), counting()));
//
//            Entry<String, Long> entryWithMaxCount = nameCountMap.entrySet().stream()
//                    .max(Map.Entry.comparingByValue())
//                    .orElse(null);
//// got them , ea barselhom ao btale3 a3la ehe
//            if (entryWithMaxCount != null) {
//                String nameWithMaxCount = entryWithMaxCount.getKey();
//                Long maxCount = entryWithMaxCount.getValue();
//
//                System.out.println("Name with the highest count: " + nameWithMaxCount);
//                System.out.println("Highest count: " + maxCount);
//            m.addAttribute("mostBookedEvents", mostBookedEvents);
//            return "admin-home.html";
//
//        }
//    }
//@GetMapping("/total-income")
//public String getTotalIncomes(Model m) {
//    List<Event> allBookedEvents = eventsJPARepository.findAll();
//    if (allBookedEvents == null) {
//        System.out.println("No booked events");
//        return "admin-home.html";
//    }
//    int totalIncomes=allBookedEvents.stream().filter(e->e.getStatus=="paid").collect(summingInt(e->e.getPrice()));
//    m.addAttribute("totalIncomes", totalIncomes);
//    return "admin-home.html";
//}
}