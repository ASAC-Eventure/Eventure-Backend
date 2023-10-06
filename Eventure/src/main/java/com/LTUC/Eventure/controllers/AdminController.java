package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AppUserEntity;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
    public void authWithHttpServletRequest(String adminUsername, String adminPassword) {
        try {
            request.login(adminUsername, adminPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/adminHome")
    public String retrieveAdminHome() {
        return "admin-home.html";
    }


    @GetMapping("/booked-events")                // all booked events + count
    public String getAllBookedEvents(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        if (allBookedEvents == null) {
            System.out.println("No booked events");
            return "admin-home.html";
        }
        m.addAttribute("bookedEvents", allBookedEvents);
        m.addAttribute("totalBookedEvents",allBookedEvents.size());
        return "admin-home.html";
    }
    @GetMapping("/unpaid-events")                // all Unpaid events + count
    public String getUnpaidEvents(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        if (allBookedEvents == null) {
            System.out.println("No unpaid events");
            return "admin-home.html";
        }
        List<Event> unpaidEvents=allBookedEvents.stream().filter(e->e.getPaymentStatus().equals("Unpaid")).collect(toList());
        m.addAttribute("unpaidEvents", unpaidEvents);
        m.addAttribute("totalUnpaidEvents",unpaidEvents.size());
        return "admin-home.html";
    }
    @GetMapping("/pending-events")                // all Pending events + count
    public String getPendingEvents(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        if (allBookedEvents == null) {
            System.out.println("No Pending events");
            return "admin-home.html";
        }
        List<Event> pendingEvents=allBookedEvents.stream().filter(e->e.getPaymentStatus().equals("Pending")).collect(toList());
        m.addAttribute("pendingEvents", pendingEvents);
        m.addAttribute("totalPendingEvents",pendingEvents.size());
        return "admin-home.html";
    }
    @GetMapping("/paid-events")                // all Paid events + count
    public String getPaidEvents(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        if (allBookedEvents == null) {
            System.out.println("No Paid events");
            return "admin-home.html";
        }
        List<Event> paidEvents=allBookedEvents.stream().filter(e->e.getPaymentStatus().equals("Paid")).collect(toList());
        m.addAttribute("paidEvents", paidEvents);
        m.addAttribute("totalPaidEvents",paidEvents.size());
        return "admin-home.html";
    }
    @PostMapping("/update-event-status/{eventId}")
    public RedirectView updateStatus_BookedEvents_toPaid(@PathVariable Long eventId) {
        try {
            Event eventRetrieved = null;
            Optional<Event> event = eventsJPARepository.findById(eventId);
            if (event.isPresent()) {
                eventRetrieved = event.get();
                eventRetrieved.setPaymentStatus("Paid");
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
//            List<Event> pendingEvents=allBookedEvents.stream().filter(s->s.getPaymentStatus().equals("Pending")).collect(toList());
//            List<Event> deadlinePaymentExceeded=pendingEvents.stream().filter(LocalDate.parse(s->s.getStartDate,formatter).compareTo(currentDate)>0).collect(toList());
//            for(Event e:deadlinePaymentExceeded ){
//            call teh function that cancel them or just send them to the admin
//            }
//        }
//    }

                                                             // most booked event + count

    @GetMapping("/most-booked")
    public String getMostBooked(Model m) {
            List<Event> allBookedEvents = eventsJPARepository.findAll();
            if (allBookedEvents == null) {
                System.out.println("No booked events");
                return "admin-home.html";
            }
            Map<String, Long> nameCountMap = allBookedEvents.stream()
                    .collect(groupingBy(s -> s.getName(), counting()));

            Map.Entry<String, Long> entryWithMaxCount = nameCountMap.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .orElse(null);

            if (entryWithMaxCount != null) {
                System.out.println("Executing getMostBooked method");
                String nameWithMaxCount = entryWithMaxCount.getKey();
                Long maxCount = entryWithMaxCount.getValue();

                m.addAttribute("mostBookedEventName", nameWithMaxCount);
                m.addAttribute("mostBookedEventCount", maxCount);
        }
        return "admin-home.html";
    }

                                                                      // most booked user + count
    @GetMapping("/most-booked-user")
    public String getMostBookedUser(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        if (allBookedEvents == null) {
            System.out.println("No one booked events");
            return "admin-home.html";
        }
        Map<Long, Long> userCountMap = allBookedEvents.stream()
                .collect(groupingBy(s -> s.getUser().getId(), counting()));

        Map.Entry<Long, Long> entryWithMaxCount = userCountMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        if (entryWithMaxCount != null) {
            Long idWithMaxCount = entryWithMaxCount.getKey();
            Long maxCount = entryWithMaxCount.getValue();

            String userNameWithMaxCount = appUserJPARepository.findNameById(idWithMaxCount);

            m.addAttribute("mostBookedUserName", userNameWithMaxCount);
            m.addAttribute("mostBookedUserCount", maxCount);
        }
        return "admin-home";
    }
    @GetMapping("/total-income")
    public String getTotalIncomes(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        if (allBookedEvents == null) {
            System.out.println("No booked events");
            return "admin-home.html";
        }
        int totalIncomes=allBookedEvents.stream().filter(e->e.getPaymentStatus().equals("Paid")).collect(summingInt(e->e.getPrice()));
        m.addAttribute("totalIncomes", totalIncomes);
        return "admin-home.html";
    }

                                                              // User Search Form
    @GetMapping("/user-search")
    public String userSearch(@RequestParam(name = "username", required = false) String username,
                             Model model){

        if (username != null && !username.isEmpty()) {
            Long userId = appUserJPARepository.findIdByName(username);
            if (userId != null) {
                List<Event> allBookedEvents = eventsJPARepository.findAll();
                List<Event> userEvents = allBookedEvents.stream()
                        .filter(event -> event.getUser().getId().equals(userId))
                        .collect(Collectors.toList());

                model.addAttribute("userEvents", userEvents);
            }
        }
        return "adminSearchedUsers.html";
    }

                                                                        // Event Search Form
    @GetMapping("/event-search")
    public String eventSearch(@RequestParam(name = "name", required = false) String eventName,Model model){
        if (eventName != null && !eventName.isEmpty()) {
                List<Event> eventDetails = eventsJPARepository.findByName(eventName);
                if (!eventDetails.isEmpty()) {
                    model.addAttribute("eventName", eventDetails);
                }
            }
            return "adminSearchedEvents.html";
    }

    @GetMapping("/admin-logout")
    public RedirectView logOut() {
        return new RedirectView("/signup");
    }
}