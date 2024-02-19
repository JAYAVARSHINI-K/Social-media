package com.example.socialmedia.controller;

import com.example.socialmedia.exception.ValidationException;
import com.example.socialmedia.model.*;
import com.example.socialmedia.service.GoogleLoginService;
import com.example.socialmedia.service.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServices userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> addUser(@RequestBody SignupModel user) {
        try {
            return ResponseEntity.ok(userService.addUser(user));
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody JwtRequestModel jwtRequestModel) {
        try {
            Map<String, Object> loginResponse = new HashMap<>();
             String token=userService.userLogin(jwtRequestModel);
            loginResponse.put("token",token);
            if (token != null) {
                return ResponseEntity.ok(loginResponse);
            } else {
                return ResponseEntity.badRequest().body(loginResponse);
            }
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> getUsers() {
        List<User> users=userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get1User/{email}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> get1User(@PathVariable String email) {
        Optional<User> user=userService.get1User(email);
        if(user.isPresent()){
            return ResponseEntity.ok(user);
        }
        else
            return (ResponseEntity<Object>) ResponseEntity.notFound();
    }

    @PostMapping("/forgot-password")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> sendResetCode(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail();
        User user = userService.findUserByEmail(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userService.generateAndSendResetCode(email);
        return ResponseEntity.ok("Reset code sent to your email");
    }

    @PostMapping("/reset-password")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        String email = request.getEmail();
        String resetCode = request.getResetCode();
        String newPassword = request.getNewPassword();

        if (userService.isResetCodeValid(email, resetCode)) {
            userService.resetPassword(email, newPassword);
            return ResponseEntity.ok("Password reset successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid reset code or expired.");
        }
    }

    @GetMapping("/get-user-profile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserProfile> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> user = userService.getCurrentUser(userDetails);
        if (user.isPresent()) {
            UserProfile userProfile = new UserProfile();
            userProfile.setFullName(user.get().getFullName());
            userProfile.setBio(user.get().getBio());
            userProfile.setProfilePhoto(user.get().getProfilePhoto());

            return ResponseEntity.ok(userProfile);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/update-profile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> updateUserProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserProfile userProfile) {
        Optional<User> user = userService.getCurrentUser(userDetails);
        if (user.isPresent()) {
            userService.updateUserProfile(user,userProfile);
            return ResponseEntity.ok("Profile updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/google-login")
    public ResponseEntity<Object> googleLogin(@RequestBody GoogleLoginModel googleLoginModel) {
        try {
            // Assuming googleLoginModel contains the Google ID token
            String googleIdToken = googleLoginModel.getGoogleIdToken();

            // Authenticate with Google
            String userEmail = GoogleLoginService.authenticate(googleIdToken);

            if (userEmail != null) {
                // User authentication successful. Proceed with your application logic.
                // You may want to check if the user is already registered in your system.
                // If not, you can register the user.

                // Example: Register the user if not already registered
                SignupModel user = new SignupModel();
                user.setEmail(userEmail);
                userService.addUser(user);

                // Issue a JWT token for the user (similar to your login logic)
                String token = userService.userLogin(new JwtRequestModel(userEmail, "password"));

                Map<String, Object> loginResponse = new HashMap<>();
                loginResponse.put("token", token);
                return ResponseEntity.ok(loginResponse);
            } else {
                // User authentication failed.
                return ResponseEntity.badRequest().body("Google authentication failed");
            }
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/public/refreshToken")
    public ResponseEntity<Object> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return userService.refreshToken(request,response);
    }
}