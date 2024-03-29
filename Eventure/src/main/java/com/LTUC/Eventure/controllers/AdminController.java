
package com.LTUC.Eventure.controllers;

import com.LTUC.Eventure.models.AddEventEntity;
import com.LTUC.Eventure.models.AppUserEntity;
import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.repositories.AddEventJPARepository;
import com.LTUC.Eventure.repositories.AppUserJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.EventsJPARepository;
import com.LTUC.Eventure.services.AdminService.AdminService;
import com.LTUC.Eventure.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

import static java.util.stream.Collectors.*;

@Controller
public class AdminController {
    private AppUserJPARepository appUserJPARepository;
    private EmailSenderService emailSenderService;
    private EventsJPARepository eventsJPARepository;

    private AdminService adminService;

    private AddEventJPARepository addEventJPARepository;
    @Autowired

    public AdminController(AppUserJPARepository appUserJPARepository, EmailSenderService emailSenderService, EventsJPARepository eventsJPARepository, AdminService adminService, AddEventJPARepository addEventJPARepository) {
        this.appUserJPARepository = appUserJPARepository;
        this.emailSenderService = emailSenderService;
        this.eventsJPARepository = eventsJPARepository;
        this.adminService = adminService;
        this.addEventJPARepository = addEventJPARepository;
    }

    @GetMapping("/adminHome")
    public String retrieveAdminHome(Model model) {
        adminService.updateStatus_unpaid_toCancelled();
        adminService.clearFinishedEvents();
        return "admin-home.html";
    }

    @GetMapping("/booked-events")                // all booked events + count
    public String getAllBookedEvents(Model m, RedirectAttributes redir) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        List<AddEventEntity> allAdminBookedEvents = addEventJPARepository.findAll().stream().filter(e -> e.getPaymentStatus() != null && e.getUser() == null).collect(toList());

        if (allBookedEvents == null && allAdminBookedEvents == null) {
            System.out.println("No events");
            return "admin-home.html";
        }

        int total = allAdminBookedEvents.size() + allBookedEvents.size();

        if (total == 0) {
            m.addAttribute("errorMessageBookedEvents", "No Booked Events Available !");
        }

        m.addAttribute("adminBookedEvents", allAdminBookedEvents);
        m.addAttribute("bookedEvents", allBookedEvents);
        m.addAttribute("totalBookedEvents", total);
        return "admin-home.html";
    }

    @GetMapping("/unpaid-events")                // all Unpaid events + count
    public String getUnpaidEvents(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        List<AddEventEntity> allAdminBookedEvents = addEventJPARepository.findAll();

        if (allBookedEvents == null && allAdminBookedEvents == null) {
            System.out.println("No  events");
            return "admin-home.html";
        }
        List<Event> unpaidEvents = allBookedEvents.stream().filter(e -> e.getPaymentStatus().equals("Unpaid")).collect(toList());
        List<AddEventEntity> unpaidAdminEvents = allAdminBookedEvents.stream().filter(e -> e.getPaymentStatus() != null && e.getPaymentStatus().equals("Unpaid")).collect(toList());
        int total = unpaidAdminEvents.size() + unpaidEvents.size();
        if (total == 0) {
            m.addAttribute("errorMessageUnpaidEvents", "No Unpaid Events Available !");
        }
        m.addAttribute("unpaidAdminEvents", unpaidAdminEvents);
        m.addAttribute("unpaidEvents", unpaidEvents);
        m.addAttribute("totalUnpaidEvents", total);
        return "admin-home.html";
    }

    @GetMapping("/pending-events")                // all Pending events + count
    public String getPendingEvents(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        List<AddEventEntity> allAdminBookedEvents = addEventJPARepository.findAll();

        if (allBookedEvents == null && allAdminBookedEvents == null) {
            System.out.println("No  events");
            return "admin-home.html";
        }
        List<Event> pendingEvents = allBookedEvents.stream().filter(e -> e.getPaymentStatus().equals("Pending")).collect(toList());
        List<AddEventEntity> pendingAdminEvents = allAdminBookedEvents.stream().filter(e -> e.getPaymentStatus() != null && e.getPaymentStatus().equals("Pending")).collect(toList());
        int total = pendingAdminEvents.size() + pendingEvents.size();
        if (total == 0) {
            m.addAttribute("errorMessagePendingEvents", "No Pending Events Available !");
        }
        m.addAttribute("pendingAdminEvents", pendingAdminEvents);
        m.addAttribute("pendingEvents", pendingEvents);
        m.addAttribute("totalPendingEvents", total);
        return "admin-home.html";
    }

    @GetMapping("/paid-events")                // all Paid events + count
    public String getPaidEvents(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        List<AddEventEntity> allAdminBookedEvents = addEventJPARepository.findAll();

        if (allBookedEvents == null && allAdminBookedEvents == null) {
            System.out.println("No  events");
            return "admin-home.html";
        }

        List<Event> paidEvents = allBookedEvents.stream().filter(e -> e.getPaymentStatus().equals("Paid")).collect(toList());

        List<AddEventEntity> paidAdminEvents = allAdminBookedEvents.stream().filter(e -> e.getPaymentStatus() != null && e.getPaymentStatus().equals("Paid")).collect(toList());
        int total = paidAdminEvents.size() + paidEvents.size();
        if (total == 0) {
            m.addAttribute("errorMessagePaidEvents", "No Paid Events Available !");
        }
        m.addAttribute("paidAdminEvents", paidAdminEvents);
        m.addAttribute("paidEvents", paidEvents);
        m.addAttribute("totalPaidEvents", total);
        return "admin-home.html";
    }

    @GetMapping("/cancelled-events")                // all Cancelled events + count
    public String getCancelledEvents(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        List<AddEventEntity> allAdminBookedEvents = addEventJPARepository.findAll();
        if (allBookedEvents == null) {
            System.out.println("No  events");
            return "admin-home.html";
        }
        List<Event> cancelledEvents = allBookedEvents.stream().filter(e -> e.getPaymentStatus().equals("Cancelled")).collect(toList());
        List<AddEventEntity> adminCancelledEvents = allAdminBookedEvents.stream().filter(e -> e.getPaymentStatus() != null && e.getPaymentStatus().equals("Cancelled")).collect(toList());
        int total = cancelledEvents.size() + adminCancelledEvents.size();
        if (total == 0) {
            m.addAttribute("errorMessageCancelledEvents", "No Cancelled Events Available !");
            return "admin-home.html";
        } else
            m.addAttribute("adminCancelledEvents", adminCancelledEvents);
        m.addAttribute("cancelledEvents", cancelledEvents);
        m.addAttribute("totalCancelledEvents", total);
        return "admin-home.html";
    }

    @GetMapping("/requested-events")
    public String getRequestedEvents(Model model) {
        List<AddEventEntity> requestedEvents = addEventJPARepository.findAll();
        long counter = requestedEvents.stream().filter(event -> !event.isApproved()).count();
        if (requestedEvents == null) {
            System.out.println("No  events");
            return "admin-home.html";
        }
        if (counter == 0) {
            model.addAttribute("errorMessageRequestedEvents", "No Requested Events Available !");
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
                emailSenderService.sendEmail("We hope this message finds you well. We would like to inform you of an important update regarding your event, the payment status for this event has been changed to Paid. Event:" + eventRetrieved.getName(), "Eventure", eventRetrieved.getUser().getEmail());
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

    @PostMapping("/update-eventCreated-status/{eventId}")
    public RedirectView updateStatus_pending_toPaid_created(@PathVariable Long eventId) {
        try {
            AddEventEntity eventRetrieved = null;
            Optional<AddEventEntity> event = addEventJPARepository.findById(eventId);
            if (event.isPresent()) {
                eventRetrieved = event.get();
                emailSenderService.sendEmail("We hope this message finds you well. We would like to inform you of an important update regarding your event, the payment status for this event has been changed to Paid. Event:" + eventRetrieved.getName(), "Eventure", eventRetrieved.getUser().getEmail());
                eventRetrieved.setPaymentStatus("Paid");
                addEventJPARepository.save(eventRetrieved);
            } else {
                return new RedirectView("/adminHome");
            }
        } catch (Exception e) {
            System.out.println("Error in changing event to paid" + e);
            return new RedirectView("/adminHome");
        }
        return new RedirectView("/paid-events");
    }


    @GetMapping("/events-statics")
    public String getEventsMap(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        if (allBookedEvents == null) {
            System.out.println("No booked events");
            return "admin-home.html";
        }
        Map<String, Long> event_countMapAdmin = addEventJPARepository.findAll().stream().filter(e -> e.getPaymentStatus() != null).collect(groupingBy(s -> s.getName(), counting()));
        ;

        Map<String, Long> event_countMap = allBookedEvents.stream()
                .collect(groupingBy(s -> s.getName(), counting()));
        event_countMap.putAll(event_countMapAdmin);
        m.addAttribute("eventStatisticsMap", event_countMap);
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
        for (AppUserEntity user : allUsers) {
            user_eventsMap.put(user.getUsername(), (long) user.getBookedEvents().size() + user.getNewEvents().size());
        }
        m.addAttribute("userStatisticsMap", user_eventsMap);
        return "admin-statics.html";
    }

    @GetMapping("/total-income")
    public String getTotalIncomes(Model m) {
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        List<AddEventEntity> allAdminBookedEvents = addEventJPARepository.findAll();

        if (allBookedEvents == null && allAdminBookedEvents == null) {
            System.out.println("No  events");
            return "admin-home.html";
        }

        int totalIncomes = allBookedEvents.stream().filter(e -> e.getPaymentStatus().equals("Paid")).collect(summingInt(e -> e.getPrice()));
        int adminTotalIncomes = allAdminBookedEvents.stream().filter(e -> e.getPaymentStatus() != null && e.getPaymentStatus().equals("Paid")).collect(summingInt(e -> e.getPrice()));
        int total = totalIncomes + adminTotalIncomes;
        if (total == 0) {
            m.addAttribute("errorMessageTotalIncome", "There is no Income yet !");
            return "admin-home.html";
        } else {
            m.addAttribute("totalIncomes", total);
        }
        return "admin-home.html";
    }

    @GetMapping("/find-user")
    public String userSearch(String username, Model model) {
        if (username != null && !username.isEmpty()) {
            AppUserEntity user = appUserJPARepository.findByUsername(username);
            if (user == null) {
                model.addAttribute("errorMessageUserInfo", "User not found !");
                return "admin-home.html";
            } else model.addAttribute("userInfo", user);
        }
        return "admin-home.html";
    }

    @GetMapping("/find-event")
    public String eventSearch(@RequestParam String eventName, Model model) {
        if (eventName != null && !eventName.isEmpty()) {
            List<Event> searchedEventList = eventsJPARepository.findByName(eventName);
            List<AddEventEntity> searchedAdminEventsList = addEventJPARepository.findAdminEventByName(eventName).stream().filter(e -> e.getUser() != null).collect(toList());
            List<String> users = new ArrayList<>();
            if (searchedAdminEventsList.size() == 0 && searchedEventList.size() == 0) {
                model.addAttribute("errorMessageEventInfo", "Event not found!");
                return "admin-home.html";
            }

            if (!searchedEventList.isEmpty()) {
                for (Event e : searchedEventList) {
                    users.add(e.getUser().getUsername());
                }
                model.addAttribute("eventsFromSearch", searchedEventList.get(0));
                model.addAttribute("eventUsers", users);
            }
            if (!searchedAdminEventsList.isEmpty()) {
                for (AddEventEntity e : searchedAdminEventsList) {
                    users.add(e.getUser().getUsername());
                }
                model.addAttribute("adminEventsFromSearch", searchedAdminEventsList.get(0));
                model.addAttribute("adminEventUsers", users);
            }
        }
        return "admin-home.html";
    }

    @GetMapping("/admin-logout")
    public RedirectView logOut() {
        return new RedirectView("/");
    }
}