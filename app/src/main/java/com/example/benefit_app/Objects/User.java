package com.example.benefit_app.Objects;

public class User {

    private String firstName, lastName, email, phoneNumber, username;

    public User(){

    }


    public User(String fName, String lName, String email, String phoneNumber, String username){

        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.lastName = lName;
        this.firstName = fName;

    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
