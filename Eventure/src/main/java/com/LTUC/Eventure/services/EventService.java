package com.LTUC.Eventure.services;

import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.models.apiEntities.Events;
import com.LTUC.Eventure.repositories.apiJPARepositories.AddressCountryJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.AddressJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.EventsJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.LocationJPARepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
<<<<<<< HEAD
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class EventService {
    EventsJPARepository eventsJPARepository;
    LocationJPARepository locationJPARepository;
    AddressJPARepository addressJPARepository;
    AddressCountryJPARepository addressCountryJPARepository;
    Events events;

=======
import java.net.URL;
import java.net.HttpURLConnection;
@Service
public class EventService {

    EventsJPARepository eventsJPARepository;

    LocationJPARepository locationJPARepository;

    AddressJPARepository addressJPARepository;

    AddressCountryJPARepository addressCountryJPARepository;

    @Autowired
    public EventService(EventsJPARepository eventsJPARepository, LocationJPARepository locationJPARepository, AddressJPARepository addressJPARepository, AddressCountryJPARepository addressCountryJPARepository) {
        this.eventsJPARepository = eventsJPARepository;
        this.locationJPARepository = locationJPARepository;
        this.addressJPARepository = addressJPARepository;
        this.addressCountryJPARepository = addressCountryJPARepository;
    }

    @Transactional
    public void fetchAndSaveEventsFromApi(String apiUrl) {
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
            save_fromAPI_toDB(events);}
         catch (IOException e) {
            System.out.println("Error in Making Connection to the API- " + e);
        }
    }

    public void save_fromAPI_toDB(Events eventsFromApi) {
        try {
            // write to this file for debug only
            WriteToFile("C:\\Users\\Saify\\IdeaProjects\\Eventure-Backend\\Eventure\\src\\main\\resources\\saif.json.txt", events);
            clearAllTablesInDB();
            // Save events to the database
            for (Event event : eventsFromApi.getEvents()) {
                addressCountryJPARepository.save(event.getLocation().getAddress().getAddressCountry());
                addressJPARepository.save(event.getLocation().getAddress());
                locationJPARepository.save(event.getLocation());
                event.setPrice((int) (50 + (Math.random() * (250 - 50))));
                eventsJPARepository.save(event);
            }
        } catch (Exception e) {
            System.out.println("Error in Saving Events to DB- " + e);
        }
    }

    public void clearAllTablesInDB() {
        eventsJPARepository.deleteAll();
        addressCountryJPARepository.deleteAll();
        addressJPARepository.deleteAll();
        locationJPARepository.deleteAll();
    }


            } else

    {
        System.out.println("Can not read and save.");
    }
        } catch (IOException e) {
            // Handle exception (e.g., connection error)
            e.printStackTrace();
        }
    }


}
