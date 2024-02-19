package com.example.socialmedia.model;

public class UserProfile {
    private boolean private_acc;
    private String fullName;
    private String bio;
    private String profilePhoto;

    public UserProfile() {
    }

    public UserProfile(boolean private_acc, String fullName, String bio, String profilePhoto) {
        this.private_acc = private_acc;
        this.fullName = fullName;
        this.bio = bio;
        this.profilePhoto = profilePhoto;
    }

    public boolean isPrivate_acc() {
        return private_acc;
    }

    public void setPrivate_acc(boolean private_acc) {
        this.private_acc = private_acc;
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

    @Override
    public String toString() {
        return "UserProfile{" +
                "private_acc=" + private_acc +
                ", fullName='" + fullName + '\'' +
                ", bio='" + bio + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                '}';
    }
}
