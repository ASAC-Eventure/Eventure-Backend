package com.LTUC.Eventure.services;

import com.LTUC.Eventure.models.apimodels.Event;
import com.LTUC.Eventure.models.apimodels.Events;
import com.LTUC.Eventure.repositories.EventsJPA;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
@Service
public class EventService {
    @Autowired
    EventsJPA eventsJPA;


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
                WriteToFile("C:\\Users\\Saify\\IdeaProjects\\Eventure-Backend\\Eventure\\src\\main\\resources\\saif.json.txt", events);

                // Save events to the database
                for (Event event : events.getEvents()) {
                    eventsJPA.save(event);
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
