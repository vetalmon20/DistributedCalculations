package com.university.model.human;

public class Human {
    private Long id;
    private Long age;
    private String name;
    private String city;
    private String language;

    public Human(Long id, Long age, String name, String city, String language) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.city = city;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "name = " + name +
                ", age = " + age +
                ", city = " + city +
                ", language = " + language;
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

        if (!(o instanceof Human)) {
            return false;
        }

        Human human = (Human) o;
        return this.name.equals(human.name);
    }
}
