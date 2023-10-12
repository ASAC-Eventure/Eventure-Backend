package com.LTUC.Eventure.services.AdminService;

import com.LTUC.Eventure.models.AddEventEntity;
import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.repositories.AddEventJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.EventsJPARepository;
import com.LTUC.Eventure.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class AdminServiceImpl implements AdminService {
    private EventsJPARepository eventsJPARepository;
    private EmailSenderService emailSenderService;
    private AddEventJPARepository addEventJPARepository;

    @Autowired
    public AdminServiceImpl(EventsJPARepository eventsJPARepository, EmailSenderService emailSenderService, AddEventJPARepository addEventJPARepository) {
        this.eventsJPARepository = eventsJPARepository;
        this.emailSenderService = emailSenderService;
        this.addEventJPARepository = addEventJPARepository;
    }

    @Override
    public void updateStatus_unpaid_toCancelled() {   // this method will make status cancelled for every unpaid status that exceeds allowed days to pay
        System.out.println("raeched update to cancell");
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        if (!allBookedEvents.isEmpty()) {
            List<Event> unpaidEvents = allBookedEvents.stream().filter(s -> s.getPaymentStatus().equals("Unpaid")).collect(toList());
            if (!unpaidEvents.isEmpty()) {
                for (Event e : unpaidEvents) {
                    String eventStartDate = e.getStartDate().split("T")[0];
                    if (compareToCurrentDate(subtractTwoDays(eventStartDate))) {
                        System.out.println("cancel from db1");
                        e.setPaymentStatus("Cancelled");
                        emailSenderService.sendEmail("We hope this message finds you well. We would like to inform you of an important update regarding your event, We regret to inform you that the payment status for this event has been cancelled. Event:" + e.getName(), "Eventure", e.getUser().getEmail());

                        eventsJPARepository.save(e);
                    }
                }
            }
        }
        //// fot the added events
        List<AddEventEntity> allBookedEvents_added = addEventJPARepository.findAll();
        if (!allBookedEvents_added.isEmpty()) {
            List<AddEventEntity> unpaidEvents_added = allBookedEvents_added.stream().filter(s -> s.getPaymentStatus() != null && s.getPaymentStatus().equals("Unpaid")).collect(toList());
            System.out.println("findded to be cancelled" + unpaidEvents_added.size());
            if (!unpaidEvents_added.isEmpty()) {
                for (AddEventEntity e : unpaidEvents_added) {
                    LocalDate startDate = e.getStartDate();
                    String eventStartDate = startDate.toString();
                    if (compareToCurrentDate(subtractTwoDays(eventStartDate))) {
                        System.out.println("cancel from db2");
                        e.setPaymentStatus("Cancelled");
                        emailSenderService.sendEmail("We hope this message finds you well. We would like to inform you of an important update regarding your event, We regret to inform you that the payment status for this event has been cancelled. Event:" + e.getName(), "Eventure", e.getUser().getEmail());
                        addEventJPARepository.save(e);
                    }
                }
            }
        }
    }


    @Override
    public void clearFinishedEvents() {   // this method will delete all events that finished from database
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        if (!allBookedEvents.isEmpty()) {
            List<Event> lastEvents = allBookedEvents.stream().filter(s -> s.getPaymentStatus().equals("Paid") || s.getPaymentStatus().equals("Cancelled")).collect(toList());
            if (!lastEvents.isEmpty()) {
                for (Event e : lastEvents) {
                    String eventEndDate = e.getEndDate().split("T")[0];
                    if (compareToCurrentDate(LocalDate.parse(eventEndDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
                        eventsJPARepository.delete(e);
                    }
                }
            }
        }
        // for the added events
        List<AddEventEntity> allBookedEvents_added = addEventJPARepository.findAll();
        if (!allBookedEvents_added.isEmpty()) {
            List<AddEventEntity> lastEvents = allBookedEvents_added.stream().filter(s -> s.getPaymentStatus() != null && (s.getPaymentStatus().equals("Paid") || s.getPaymentStatus().equals("Cancelled"))).collect(toList());
            if (!lastEvents.isEmpty()) {
                for (AddEventEntity e : lastEvents) {
                    LocalDate date = e.getEndDate();
                    String eventEndDate = date.toString().split("T")[0];
                    if (compareToCurrentDate(LocalDate.parse(eventEndDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
                        addEventJPARepository.delete(e);
                    }
                }
            }
        }
    }

    @Override
    public LocalDate subtractTwoDays(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formattedDate = LocalDate.parse(date, formatter);  // converted the string date to localDate
        LocalDate twoDaysBefore = formattedDate.minusDays(2);
        return twoDaysBefore;
    }

    @Override
    public boolean compareToCurrentDate(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.isAfter(date);
    }
}