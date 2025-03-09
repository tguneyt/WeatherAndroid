package com.example.weather;

public class City_ListCL {

    int id;
    String city;
    int population;
    String region;
    String country;


    public City_ListCL(String city, int population, String region, String country) {
        this.city = city;
        this.population = population;
        this.region = region;
        this.country = country;
    }

    public City_ListCL(int id, String city, int population, String region, String country) {
        this.id = id;
        this.city = city;
        this.population = population;
        this.region = region;
        this.country = country;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
