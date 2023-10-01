package com.LTUC.Eventure.services;

import com.LTUC.Eventure.models.apimodels.Event;
import com.LTUC.Eventure.models.apimodels.Events;
import com.LTUC.Eventure.models.apimodels.Offer;
import com.LTUC.Eventure.models.apimodels.PriceSpecification;
import com.LTUC.Eventure.repositories.*;

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
    @Autowired
    TheEventJPA theEventJPA;
    @Autowired
    LocationJPA locationJPA;
    @Autowired
    AddressCountryJPA addressCountryJPA;
    @Autowired
    AddressJPA addressJPA;
    @Autowired
    PriceSpecificationJPA priceSpecificationJPA;
    @Autowired
    OfferJPA offerJPA;


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
                theEventJPA.deleteAll();
                addressCountryJPA.deleteAll();
                addressJPA.deleteAll();
                locationJPA.deleteAll();
                // Save events to the database
                for (Event event : events.getEvents()) {
                    // Create a new Offer entity
//                    Offer newOffer = new Offer();
//                    if (!event.getOffers().isEmpty()) {
//                        Offer firstOffer = event.getOffers().get(0); // Get the first offer
//
//                        if (firstOffer.getPriceSpecification() != null) {
//                            // If there is a non-empty priceSpecification, set it on the newOffer
//                            PriceSpecification priceSpec = firstOffer.getPriceSpecification();
//
//                            // Check if the PriceSpecification is already saved in the database
//                            if (priceSpec.getId() == null) {
//                                // If not, save it before setting it in the newOffer
//                                priceSpecificationJPA.save(priceSpec);
//                                System.out.println("saved id for price ="+priceSpec.getId());
//                            }
//
//                            newOffer.setPriceSpecification(priceSpec);
//                        } else {
//                            // Handle the case where priceSpecification is empty or null
//                            // Set default values for the PriceSpecification properties
//                            PriceSpecification defaultPriceSpec = new PriceSpecification();
//                            defaultPriceSpec.setPrice("0.0"); // Set to a default price or other appropriate value
//                            defaultPriceSpec.setPriceCurrency("CAD"); // Set to a default currency or other appropriate value
//
//                            // Save the default PriceSpecification
//                            priceSpecificationJPA.save(defaultPriceSpec);
//
//                            newOffer.setPriceSpecification(defaultPriceSpec);
//                        }
//                    } else {
//                        // Handle the case where there are no offers
//                        // Set default values for the PriceSpecification properties
//                        PriceSpecification defaultPriceSpec = new PriceSpecification();
//                        defaultPriceSpec.setPrice("0.0"); // Set to a default price or other appropriate value
//                        defaultPriceSpec.setPriceCurrency("CAD"); // Set to a default currency or other appropriate value
//
//                        // Save the default PriceSpecification
//                        priceSpecificationJPA.save(defaultPriceSpec);
//
//                        newOffer.setPriceSpecification(defaultPriceSpec);
//                    }
//
//                    // Set other properties of the Offer entity
//                    newOffer.setUrl(""); // You can set the URL as needed
//event
                    // Save the newOffer entity
                    //offerJPA.save(newOffer);

                    // Save other related entities as needed (address, location, event)
                    addressCountryJPA.save(event.getLocation().getAddress().getAddressCountry());
                    addressJPA.save(event.getLocation().getAddress());
                    locationJPA.save(event.getLocation());
                    theEventJPA.save(event);
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
//    private Location mapLocationFromApiData(ApiLocationData apiLocationData) {
//        Location location = new Location();
//        location.setStreetAddress(apiLocationData.getStreetAddress());
//        location.setAddressLocality(apiLocationData.getAddressLocality());
//        // Set other properties based on the API data
//        return location;
//    }
    public void WriteToFile(String path, Events events) {  // for testing
        try (FileWriter writer = new FileWriter(new File(path))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(events, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
