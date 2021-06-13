package com.university.model.human;

public class HumanBuilder {
    private Long id;
    private Long age;
    private String name;
    private String city;
    private String language;

    public HumanBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public HumanBuilder setAge(Long age) {
        this.age = age;
        return this;
    }

    public HumanBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public HumanBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public HumanBuilder setLanguage(String language) {
        this.language = language;
        return this;
    }

    public Human build() {
        return new Human(id, age, name, city, language);
    }
}