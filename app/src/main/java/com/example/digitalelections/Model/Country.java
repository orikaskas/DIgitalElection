package com.example.digitalelections.Model;

import java.util.ArrayList;
import java.util.List;

public class Country {
    private List<City> cities;

    public Country() {
        this.cities = new ArrayList<>();
    }

    public void addCity(City city) {
        cities.add(city);
    }

    // Getters and setters
    public List<City> getCities() {
        return cities;
    }
}
