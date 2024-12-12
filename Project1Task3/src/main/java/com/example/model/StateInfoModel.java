package com.example.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class StateInfoModel {

    public String getStatePopulation(String stateName, String stateData) {
        // Parse JSON response to get state population
        String statePopulation = "";
        com.google.gson.JsonArray jsonArray = new com.google.gson.JsonParser().parse(stateData).getAsJsonArray();
        for (com.google.gson.JsonElement element : jsonArray) {
            com.google.gson.JsonArray state = element.getAsJsonArray();
            String name = state.get(0).getAsString();
            String population = state.get(1).getAsString();
            if (name.equalsIgnoreCase(stateName)) {
                statePopulation = population;
                break;
            }
        }
        return statePopulation;
    }

    public String getStateData() {
        // Return the JSON response from the website
        return "[[\"Pennsylvania\",\"13002700\",\"42\"], [\"California\",\"39538223\",\"06\"], ...]";
    }

    public String getStatePopulation(String stateName) throws UnsupportedEncodingException {
        stateName = URLEncoder.encode(stateName, "UTF-8");
        String response = "";
        String censusURL = "https://api.census.gov/data/2020/dec/pl?get=NAME,P1_001N&for=state:*" + stateName;
        response = fetch(censusURL);
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(response);
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        // Initialize variables to hold state name and population
        String statePopulation = "";
        String stateCode = "";

        // Loop through each element in the JSON array
        for (JsonElement element : jsonArray) {
            JsonArray stateData = element.getAsJsonArray();
            String name = stateData.get(0).getAsString();
            String population = stateData.get(1).getAsString();
            String code = stateData.get(2).getAsString();

            // Check if the current state name matches the requested state name
            if (name.equalsIgnoreCase(stateName)) {
                statePopulation = population;
                stateCode = code;
                break; // Exit loop if state is found
            }
        }
        return statePopulation;
    }

    public String getStateImageURL(String stateName, String thing) {
        String imageURL = "";
        // Code to fetch state image URL based on the thing (state flower, state flag, etc.)
        return imageURL;
    }

    // Other methods for fetching additional state information from various sources

    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                response += str;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
        return response;
    }
}
