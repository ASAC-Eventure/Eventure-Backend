package com.LTUC.Eventure.services;

import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.models.apiEntities.Events;
import com.LTUC.Eventure.repositories.apiJPARepositories.AddressCountryJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.AddressJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.EventsJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.LocationJPARepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    EventsJPARepository eventsJPARepository;
    LocationJPARepository locationJPARepository;
    AddressJPARepository addressJPARepository;
    AddressCountryJPARepository addressCountryJPARepository;
    Events events;
    @Value("${apiSecretKey}")
    String myKey;

    @Autowired
    public EventService(EventsJPARepository eventsJPARepository, LocationJPARepository locationJPARepository, AddressJPARepository addressJPARepository, AddressCountryJPARepository addressCountryJPARepository) {
        this.eventsJPARepository = eventsJPARepository;
        this.locationJPARepository = locationJPARepository;
        this.addressJPARepository = addressJPARepository;
        this.addressCountryJPARepository = addressCountryJPARepository;
    }

    @Transactional
    public Events fetchAndSaveEventsFromApi(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                Gson gson = new Gson();
                StringBuilder apiData = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    apiData.append(line);
                }
                reader.close();
                String json = apiData.toString();
                events = gson.fromJson(json, Events.class);

            }
            connection.disconnect();
            //save_fromAPI_toDB(events);

        } catch (IOException e) {
            // Handle exception (e.g., connection error)
            System.out.println("Error in Making Connection to the API- " + e);
        }
        return events;
    }


    public Events getEvents_By_CountryName_and_startDate(String countryName, LocalDate startDate) {
        String countryISO2 = "";
        String[] countryNameArr = countryName.toUpperCase().trim().split(" ");
        if (countryNameArr.length >= 2)
            countryISO2 = String.valueOf(countryNameArr[0].charAt(0)) + countryNameArr[1].charAt(0);
        else
            countryISO2 = String.valueOf(countryNameArr[0].charAt(0)) + countryNameArr[0].charAt(1);
        System.out.println(countryISO2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateString = startDate.format(formatter);
        String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=" + myKey + "&geoCountryIso2=" + countryISO2 + "&eventDateFrom=" + startDateString;
        Events country_dateEvents = fetchAndSaveEventsFromApi(apiData);
        for (Event e : country_dateEvents.getEvents()) {
            e.setPrice((int) (50 + (Math.random() * (250 - 50))));
        }
        return country_dateEvents;
    }

    public Events getEvents_By_CountryName(String countryName) {
        String countryISO2 = "";
        String[] countryNameArr = countryName.toUpperCase().trim().split(" ");
        if (countryNameArr.length >= 2)
            countryISO2 = String.valueOf(countryNameArr[0].charAt(0)) + countryNameArr[1].charAt(0);
        else
            countryISO2 = String.valueOf(countryNameArr[0].charAt(0)) + countryNameArr[0].charAt(1);
        System.out.println(countryISO2);
        String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=" + myKey + "&geoCountryIso2=" + countryISO2;
        Events countryEvents = fetchAndSaveEventsFromApi(apiData);
        for (Event e : countryEvents.getEvents()) {
            e.setPrice((int) (50 + (Math.random() * (250 - 50))));
        }
        return countryEvents;
    }

    public Events getEvents_By_startDate(LocalDate startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateString = startDate.format(formatter);
        String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=" + myKey + "&eventDateFrom=" + startDateString;
        Events dateEvents = fetchAndSaveEventsFromApi(apiData);
        for (Event e : dateEvents.getEvents()) {
            e.setPrice((int) (50 + (Math.random() * (250 - 50))));
        }
        return dateEvents;
    }

    public List<Event> get10RandomEvents() {
        String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=" + myKey;
        Events randomEvents = fetchAndSaveEventsFromApi(apiData);
        List<Event> mostRatedEvents = randomEvents.getEvents().stream().limit(10).collect(Collectors.toList());
        for (Event e : mostRatedEvents) {
            e.setPrice((int) (50 + (Math.random() * (250 - 50))));
        }
        return mostRatedEvents;
    }


}