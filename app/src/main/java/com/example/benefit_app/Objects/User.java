package com.example.benefit_app.Objects;

public class User {

    private String firstName, lastName, email, phoneNumber, username, birthDate, gender, weight, height;

    public User(){

    }


    public User(String fName, String lName, String email, String phoneNumber, String username, String birthDate1, String gender1,
                String weight1, String height1){

        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.lastName = lName;
        this.firstName = fName;
        this.weight = weight1;
        this.height = height1;
        this.birthDate = birthDate1;
        this.gender = gender1;

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

    public String getBirthDate() {
        return birthDate;
    }

    public String getWeight() {
        return weight;
    }


    public String getHeight() {
        return height;
    }

    public String getGender() {
        return gender;
    }
}
