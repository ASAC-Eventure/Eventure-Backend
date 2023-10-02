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
            // Open a connection to the API URL
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method (GET in this case)
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read and discard the first 10 lines
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
                // Read and process the JSON data
                Gson gson = new Gson();
                StringBuilder apiData = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    apiData.append(line);
                }
                reader.close();

                // Deserialize the JSON data
                String json = apiData.toString();
//                Event[] events = gson.fromJson(json, Event[].class);
                Events events = gson.fromJson(json, Events.class);
                WriteToFile("C:\\Users\\USER\\newforGradle\\EventureBackend\\Eventure-Backend\\Eventure\\src\\main\\resources\\saif.json.txt", events);
                eventsJPARepository.deleteAll();
                addressCountryJPARepository.deleteAll();
                addressJPARepository.deleteAll();
                locationJPARepository.deleteAll();
                // Save events to the database
                for (Event event : events.getEvents()) {
                    addressCountryJPARepository.save(event.getLocation().getAddress().getAddressCountry());
                    addressJPARepository.save(event.getLocation().getAddress());
                    locationJPARepository.save(event.getLocation());
                    event.setPrice((int) Math.floor(50 + (Math.random() * (250 - 50))));
                    eventsJPARepository.save(event);
                }
            } else {
                System.out.println("Can not read and save.");
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            // Handle exception (e.g., connection error)
            e.printStackTrace();
        }
    }

    public void WriteToFile(String path, Events events) {  // for testing
        try (FileWriter writer = new FileWriter(new File(path))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(events, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
