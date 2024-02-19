package com.example.socialmedia.service;

import com.example.socialmedia.model.JwtRequestModel;
import com.example.socialmedia.model.SignupModel;
import com.example.socialmedia.model.User;
import com.example.socialmedia.model.UserProfile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserServices {
    public Object addUser(SignupModel user);

    public String userLogin(JwtRequestModel userLogin);

    List<User> getUsers();

    Optional<User> get1User(String email);

    UUID generateResetCode();

    void sendResetEmail(String email, UUID resetCode);

    User findUserByEmail(String email);
    void generateAndSendResetCode(String email);
    boolean isResetCodeValid(String email, String resetCode);
    void resetPassword(String email, String newPassword);

    Optional<User> getCurrentUser(UserDetails userDetails);

    void updateUserProfile(Optional<User> user, UserProfile userProfile);

    ResponseEntity<Object> refreshToken(HttpServletRequest request, HttpServletResponse response);
}
