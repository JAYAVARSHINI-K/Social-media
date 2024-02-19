package com.example.socialmedia.model;

import jakarta.persistence.Column;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table()
public class User implements Serializable {

    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private LocalDate dob;
    private String gender;
    private String roles;
    private UUID resetCode;
    private LocalDateTime expiration;

    //user profile
    @Column(columnDefinition = "boolean default false")
    private boolean privateAcc;
    private String fullName;
    private String bio;
    private String profilePhoto;

    public User() {
    }

    public User(String email, String firstName, String lastName, String password, LocalDate dob, String gender, String roles) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.dob = dob;
        this.gender = gender;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public UUID getResetCode() {
        return resetCode;
    }

    public void setResetCode(UUID resetCode) {
        this.resetCode = resetCode;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public boolean isPrivate_acc() {
        return privateAcc;
    }

    public void setPrivate_acc(boolean private_acc) {
        this.privateAcc = private_acc;

    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }

}