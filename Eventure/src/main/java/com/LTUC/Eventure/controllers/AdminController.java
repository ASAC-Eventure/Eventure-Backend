
package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.models.AddEventEntity;
import com.LTUC.Eventure.repositories.AddEventJPARepository;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.EventsJPARepository;
import com.LTUC.Eventure.services.AdminService.AdminService;
import com.LTUC.Eventure.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.stream.Collectors.*;

@Controller
public class AdminController {
    @Autowired
    private AppUserJPARepository appUserJPARepository;
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    private EventsJPARepository eventsJPARepository;
    @Autowired
    private AdminService adminService;
    @Autowired
    AddEventJPARepository addEventJPARepository;

    PasswordEncoder passwordEncoder;
    HttpServletRequest request;


    @Autowired
    public AdminController(AppUserJPARepository appUserJPARepository, EventsJPARepository eventsJPARepository, AdminService adminService) {
        this.appUserJPARepository = appUserJPARepository;
        this.eventsJPARepository = eventsJPARepository;
        this.adminService = adminService;
    }

    @GetMapping("/adminHome")
    public String retrieveAdminHome(Model model) {
        adminService.updateStatus_unpaid_toCancelled();
        adminService.clearFinishedEvents();
        return "admin-home.html";
    }

    @GetMapping("/booked-events")                // all booked events + count
    public String getAllBookedEvents(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        List<AddEventEntity> allAdminBookedEvents=addEventJPARepository.findAll();

        if (allBookedEvents == null && allAdminBookedEvents == null) {
            System.out.println("No  events");
            return "admin-home.html";
        }
        int total=allAdminBookedEvents.size()+allBookedEvents.size();
        m.addAttribute("adminBookedEvents", allAdminBookedEvents);
        m.addAttribute("bookedEvents", allBookedEvents);
        m.addAttribute("totalBookedEvents", total);
        return "admin-home.html";
    }

    @GetMapping("/unpaid-events")                // all Unpaid events + count
    public String getUnpaidEvents(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        List<AddEventEntity> allAdminBookedEvents=addEventJPARepository.findAll();

        if (allBookedEvents == null && allAdminBookedEvents == null) {
            System.out.println("No  events");
            return "admin-home.html";
        }
        List<Event> unpaidEvents = allBookedEvents.stream().filter(e -> e.getPaymentStatus().equals("Unpaid")).collect(toList());
        List<AddEventEntity> unpaidAdminEvents=allAdminBookedEvents.stream().filter(e -> e.getPaymentStatus().equals("Unpaid")).collect(toList());
        int total=unpaidAdminEvents.size()+unpaidEvents.size();
        m.addAttribute("unpaidAdminEvents",unpaidAdminEvents);
        m.addAttribute("unpaidEvents", unpaidEvents);
        m.addAttribute("totalUnpaidEvents", total);
        return "admin-home.html";
    }

    @GetMapping("/pending-events")                // all Pending events + count
    public String getPendingEvents(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        List<AddEventEntity> allAdminBookedEvents=addEventJPARepository.findAll();

        if (allBookedEvents == null && allAdminBookedEvents == null) {
            System.out.println("No  events");
            return "admin-home.html";
        }
        List<Event> pendingEvents = allBookedEvents.stream().filter(e -> e.getPaymentStatus().equals("Pending")).collect(toList());
        List<AddEventEntity> pendingAdminEvents=allAdminBookedEvents.stream().filter(e -> e.getPaymentStatus().equals("Pending")).collect(toList());
        int total=pendingAdminEvents.size()+pendingEvents.size();

        m.addAttribute("pendingAdminEvents", pendingAdminEvents);
        m.addAttribute("pendingEvents", pendingEvents);
        m.addAttribute("totalPendingEvents", total);
        return "admin-home.html";
    }

    @GetMapping("/paid-events")                // all Paid events + count
    public String getPaidEvents(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        List<AddEventEntity> allAdminBookedEvents=addEventJPARepository.findAll();

        if (allBookedEvents == null && allAdminBookedEvents == null) {
            System.out.println("No  events");
            return "admin-home.html";
        }

        List<Event> paidEvents = allBookedEvents.stream().filter(e -> e.getPaymentStatus().equals("Paid")).collect(toList());
        List<AddEventEntity> paidAdminEvents = allAdminBookedEvents.stream().filter(e -> e.getPaymentStatus().equals("Paid")).collect(toList());
        int total=paidAdminEvents.size()+paidEvents.size();

        m.addAttribute("paidAdminEvents", paidAdminEvents);
        m.addAttribute("paidEvents", paidEvents);
        m.addAttribute("totalPaidEvents", total);
        return "admin-home.html";
    }

    @GetMapping("/cancelled-events")                // all Cancelled events + count
    public String getCancelledEvents(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        if (allBookedEvents == null) {
            System.out.println("No  events");
            return "admin-home.html";
        }
        List<Event> cancelledEvents = allBookedEvents.stream().filter(e -> e.getPaymentStatus().equals("Cancelled")).collect(toList());
        m.addAttribute("cancelledEvents", cancelledEvents);
        m.addAttribute("totalCancelledEvents", cancelledEvents.size());
        return "admin-home.html";
    }


    @GetMapping("/requested-events")
    public String getRequestedEvents(Model model){
        List<AddEventEntity> requestedEvents = addEventJPARepository.findAll();
        long counter = requestedEvents.stream().filter(event -> !event.isApproved()).count();
        if (requestedEvents == null) {
            System.out.println("No  events");
            return "admin-home.html";
        }
        model.addAttribute("requestedEvents", requestedEvents);
        model.addAttribute("totalRequestedEvents", counter);
        return "admin-home.html";
    }

    @PostMapping("/update-event-status/{eventId}")
    public RedirectView updateStatus_pending_toPaid(@PathVariable Long eventId) {
        try {
            Event eventRetrieved = null;
            Optional<Event> event = eventsJPARepository.findById(eventId);
            if (event.isPresent()) {
                eventRetrieved = event.get();
                emailSenderService.sendEmail("Hello, Your Event "+eventRetrieved.getName()+" Payment Status is Paid.","Eventure" ,eventRetrieved.getUser().getEmail() );
                eventRetrieved.setPaymentStatus("Paid");
                eventsJPARepository.save(eventRetrieved);
            } else {
                return new RedirectView("/adminHome");
            }
        } catch (Exception e) {
            System.out.println("Error in changing event to paid" + e);
            return new RedirectView("/adminHome");
        }
        return new RedirectView("/pending-events");

    }

    @GetMapping("/events-statics")
    public String getEventsMap(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        List<AddEventEntity> allAdminBookedEvents=addEventJPARepository.findAll();

        if (allBookedEvents == null && allAdminBookedEvents == null) {
            System.out.println("No  events");
            return "admin-home.html";
        }

            Map<String, Long> event_countMap = allBookedEvents.stream()
                    .collect(groupingBy(s -> s.getName(), counting()));

            Map<String, Long> adminEvent_countMap = allAdminBookedEvents.stream()
                .collect(groupingBy(s -> s.getName(), counting()));

        Map<String, Long> mergedMap = new HashMap<>(event_countMap);
        adminEvent_countMap.forEach((key, value) -> mergedMap.merge(key, value, Long::sum));

        m.addAttribute("eventStatisticsMap", mergedMap);
        return "admin-statics.html";
    }

    @GetMapping("/users-statics")
    public String getUsersMap(Model m) {
        List<AppUserEntity> allUsers = appUserJPARepository.findAll();
        if (allUsers == null) {
            System.out.println("No booked events");
            return "admin-home.html";
        }
        Map<String, Long> user_eventsMap = new HashMap<>();
        for(AppUserEntity user: allUsers){
            user_eventsMap.put(user.getUsername(), (long) user.getBookedEvents().size());
        }
        m.addAttribute("userStatisticsMap", user_eventsMap);
        return "admin-statics.html";
    }

    @GetMapping("/total-income")
    public String getTotalIncomes(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        List<AddEventEntity> allAdminBookedEvents=addEventJPARepository.findAll();

        if (allBookedEvents == null && allAdminBookedEvents == null) {
            System.out.println("No  events");
            return "admin-home.html";
        }

        int totalIncomes = allBookedEvents.stream().filter(e -> e.getPaymentStatus().equals("Paid")).collect(summingInt(e -> e.getPrice()));
        int adminTotalIncomes = allAdminBookedEvents.stream().filter(e -> e.getPaymentStatus().equals("Paid")).collect(summingInt(e -> e.getPrice()));

        int total= totalIncomes+adminTotalIncomes;
        m.addAttribute("totalIncomes", total);
        return "admin-home.html";
    }

    @GetMapping("/find-user")
    public String userSearch(String username, Model model) {
        if (username != null && !username.isEmpty()) {
            AppUserEntity user = appUserJPARepository.findByUsername(username);
            if (user != null) {
                model.addAttribute("userInfo", user);
            }
        }
        return "admin-home.html";
    }

    @GetMapping("/find-event")
    public String eventSearch(String eventName, Model model) {
        if (eventName != null && !eventName.isEmpty()) {
            List<Event> searchedEventList = eventsJPARepository.findByName(eventName);
            model.addAttribute("eventsFromSearch", searchedEventList);
        }
        return "admin-home.html";
    }
    @GetMapping("/admin-logout")
    public RedirectView logOut() {
        return new RedirectView("/");
    }
}