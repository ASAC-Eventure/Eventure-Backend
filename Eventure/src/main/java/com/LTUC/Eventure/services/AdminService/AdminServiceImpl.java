package com.LTUC.Eventure.services.AdminService;

import com.LTUC.Eventure.models.apiEntities.Event;
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

@Autowired
    public AdminServiceImpl(EventsJPARepository eventsJPARepository, EmailSenderService emailSenderService) {
    this.eventsJPARepository = eventsJPARepository;
    this.emailSenderService = emailSenderService;
}


    @Override
    public void updateStatus_unpaid_toCancelled() {   // this method will make status cancelled for every unpaid status that exceeds allowed days to pay
        List<Event> allBookedEvents = eventsJPARepository.findAll();
        if (!allBookedEvents.isEmpty()) {
            List<Event> unpaidEvents = allBookedEvents.stream().filter(s -> s.getPaymentStatus().equals("Unpaid")).collect(toList());
            if (!unpaidEvents.isEmpty()) {
                for (Event e : unpaidEvents) {
                    String eventStartDate = e.getStartDate().split("T")[0];
                    if (compareToCurrentDate(subtractTwoDays(eventStartDate))) {
                        e.setPaymentStatus("Cancelled");
                        emailSenderService.sendEmail("Hello, Your Event "+e.getName()+" Payment Status is Cancelled.","Eventure" ,e.getUser().getEmail() );
                        eventsJPARepository.save(e);
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
