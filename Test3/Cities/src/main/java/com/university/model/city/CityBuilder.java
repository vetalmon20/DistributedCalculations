package com.university.model.city;

public class CityBuilder {
    private Long id;
    private Long year;
    private Long area;
    private Long population;
    private String name;

    public CityBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public CityBuilder setYear(Long year) {
        this.year = year;
        return this;
    }

    public CityBuilder setArea(Long area) {
        this.area = area;
        return this;
    }

    public CityBuilder setPopulation(Long population) {
        this.population = population;
        return this;
    }

    public CityBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public City build() {
        return new City(id, year, area, population, name);
    }
}