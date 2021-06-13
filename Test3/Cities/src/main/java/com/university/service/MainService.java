package com.university.service;

import com.university.dao.CityDaoTask10;
import com.university.dao.DaoFactory;
import com.university.dao.PeopleDaoTask10;
import com.university.model.city.City;
import com.university.model.human.Human;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainService {
    private final PeopleDaoTask10 peopleDaoTask10;
    private final CityDaoTask10 cityDaoTask10;

    public MainService() {
        this.peopleDaoTask10 = DaoFactory.createPeopleDao();
        this.cityDaoTask10 = DaoFactory.createCityDao();
    }

    public String getPeopleWithParams(String city, String language) {
        List<Human> people = peopleDaoTask10.getPeopleByParameters(city, language);
        return getCollectionStuff(people);
    }

    public String getCitiesWithLanguage(String language) {
        List<Human> people = peopleDaoTask10.getPeopleByLanguage(language);
        Set<City> cities = new HashSet<>();
        for(Human human : people) {
            cities.add(cityDaoTask10.getCityByName(human.getCity()));
        }

        return getCollectionStuff(cities);
    }

    public String getCitiesWithPopulation(Long population) {
        City city = cityDaoTask10.getCityByPopulation(population);
        List<Human> people = peopleDaoTask10.getPeopleByCity(city.getName());
        System.out.println("City: " + city + System.lineSeparator() + "People:");
        return getCollectionStuff(people);
    }

    public String getOldestPeople() {
        List<Human> people = peopleDaoTask10.getOldestPeople();
        return getCollectionStuff(people);
    }

    private <E> String getCollectionStuff(Collection<E> list) {
        if(list == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(E e : list) {
            sb.append(e).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
