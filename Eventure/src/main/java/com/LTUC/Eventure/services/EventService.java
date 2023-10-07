package com.LTUC.Eventure.services;

import com.LTUC.Eventure.models.apiEntities.Event;
import com.LTUC.Eventure.models.apiEntities.Events;
import com.LTUC.Eventure.repositories.apiJPARepositories.AddressCountryJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.AddressJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.EventsJPARepository;
import com.LTUC.Eventure.repositories.apiJPARepositories.LocationJPARepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;;
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

//    @Transactional
//    public Events fetchAndSaveEventsFromApi(String apiUrl) {
//        try {
//            URL url = new URL(apiUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                Gson gson = new Gson();
//                StringBuilder apiData = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    apiData.append(line);
//                }
//                reader.close();
//                String json = apiData.toString();
//                events = gson.fromJson(json, Events.class);
//                WriteToFile("C:\\Users\\Saify\\IdeaProjects\\Eventure-Backend\\Eventure\\src\\main\\resources\\saif.json.txt", events);
//
//            }
//            connection.disconnect();
//            //save_fromAPI_toDB(events);
//
//        } catch (IOException e) {
//            // Handle exception (e.g., connection error)
//            System.out.println("Error in Making Connection to the API- " + e);
//        }
//        return events;
//    }

    public Events fetchAndSaveEventsFromApi(String apiUrl) {
        Events events = null;
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) // 5 seconds timeout for connection establishment
                .setSocketTimeout(5000) // 5 seconds timeout for data retrieval
                .build();

        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {
            HttpGet request = new HttpGet(apiUrl);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    try (InputStream inputStream = response.getEntity().getContent()) {
                        String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                        events = parseJson(json);
                    } catch (IOException e) {
                        System.out.println("Error processing API response: " + e.getMessage());
                    }
                } else {
                    // Handle non-200 status code
                    System.out.println("API returned non-OK status: " + response.getStatusLine().getStatusCode());
                }
            }
        } catch (IOException e) {
            // Handle connection or IO error
            System.out.println("Error in Making Connection to the API: " + e.getMessage());
        }
        return events;
    }

    private Events parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Events.class);
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
        return countryEvents;
    }
    public Events getEvents_By_startDate(LocalDate startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateString = startDate.format(formatter);
        String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=" + myKey + "&eventDateFrom=" + startDateString;
        Events dateEvents = fetchAndSaveEventsFromApi(apiData);
        return dateEvents;
    }
//    public List<Event> get10RandomEvents() {
//        String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=" + myKey;
//        Events randomEvents = fetchAndSaveEventsFromApi(apiData);
//        List<Event> mostRatedEvents = randomEvents.getEvents().stream().limit(10).collect(Collectors.toList());
//        return mostRatedEvents;
//    }


    public List<Event> get10RandomEvents() {
        String apiData = "https://www.jambase.com/jb-api/v1/events?apikey=" + myKey;
        Events randomEvents = fetchAndSaveEventsFromApi(apiData);

        if (randomEvents != null && randomEvents.getEvents() != null) {
            List<Event> mostRatedEvents = randomEvents.getEvents().stream().limit(10).collect(Collectors.toList());
            return mostRatedEvents;
        } else {
            // Handle the case where randomEvents or its events property is null
            return Collections.emptyList(); // or return an appropriate default value
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
        addressCountryJPARepository.deleteAll();
        addressJPARepository.deleteAll();
        locationJPARepository.deleteAll();
        eventsJPARepository.deleteAll();
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