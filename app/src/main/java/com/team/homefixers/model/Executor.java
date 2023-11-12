package com.team.homefixers.model;

public class Executor {
    private String id;
    private String name;
    private String email;
    private String password;
    private String numberPhone;
    private String city;
    private String specialization;
    private String description;
    private int price;
    private String experience;
    private String contract;
    private String guarantee;
    private String urgency;

    public Executor(
            String id,
            String name,
            String email,
            String password,
            String numberPhone,
            String city,
            String specialization,
            String description,
            int price,
            String experience,
            String contract,
            String guarantee,
            String urgency
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.numberPhone = numberPhone;
        this.city = city;
        this.specialization = specialization;
        this.description = description;
        this.price = price;
        this.experience = experience;
        this.contract = contract;
        this.guarantee = guarantee;
        this.urgency = urgency;
    }

    public Executor() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public String getCity() {
        return city;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getExperience() {
        return experience;
    }

    public String getContract() {
        return contract;
    }

    public String getGuarantee() {
        return guarantee;
    }

    public String getUrgency() {
        return urgency;
    }

    @Override
    public String toString() {
        return "Executor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", city='" + city + '\'' +
                ", specialization='" + specialization + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", experience='" + experience + '\'' +
                ", contract='" + contract + '\'' +
                ", guarantee='" + guarantee + '\'' +
                ", urgency='" + urgency + '\'' +
                '}';
    }
}
