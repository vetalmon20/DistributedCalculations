package com.university.model.city;

import com.university.model.human.Human;

public class City {
    private Long id;
    private Long year;
    private Long area;
    private Long population;
    private String name;

    public City(Long id, Long year, Long area, Long population, String name) {
        this.id = id;
        this.year = year;
        this.area = area;
        this.population = population;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "Name = " + name +
                ", year = " + year +
                ", area = " + area +
                ", population = " + population;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof City)) {
            return false;
        }

        City city = (City) o;
        return this.name.equals(city.name);
    }
}
