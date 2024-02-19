package com.example.socialmedia.service;


import com.example.socialmedia.Repository.UserRepository;
import com.example.socialmedia.config.UserInfoUserDetails;
import com.example.socialmedia.exception.ValidationException;
import com.example.socialmedia.model.JwtRequestModel;
import com.example.socialmedia.model.SignupModel;
import com.example.socialmedia.model.User;
import com.example.socialmedia.model.UserProfile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    public UserServicesImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Object addUser(SignupModel user) {

        //validate email
        String email=user.getEmail();
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if(email==null || !matcher.matches()){
            throw new ValidationException("Enter valid Email.");
        }
        if(userDao.existsByEmail(email)){
            throw new ValidationException("Email already taken.");
        }

        //validate name
        String firstName=user.getFirstName();
        String NAME_REGEX = "^[a-zA-Z]+$";
        Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
        matcher = NAME_PATTERN.matcher(firstName);
        if(firstName==null || !matcher.matches()){
            throw new ValidationException("Enter valid firstName.");
        }

        String lastName=user.getLastName();
        matcher = NAME_PATTERN.matcher(lastName);
        if(!matcher.matches()){
            throw new ValidationException("Enter valid lastName.");
        }

        //validate password
        String password=user.getPassword();
        if(password.length()<8){
            throw new ValidationException("password should contain more than 8 characters");
        }
        if(!password.matches(".*[A-Z].*")){
            throw new ValidationException("password should contain at least 1 uppercase");
        }
        if(!password.matches(".*[a-z].*")){
            throw new ValidationException("password should contain at least 1 lowercase");
        }
        if(!password.matches(".*\\d.*")){
            throw new ValidationException("password should contain at least 1 digit");
        }
        if(!password.matches(".*[!@#$%^&*()_+{}|:\"<>?~].*")){
            throw new ValidationException("password should contain at least 1 special character");
        }

        //validate dob
        LocalDate dob=user.getDob();
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(dob, currentDate);
        int minAge = 1;
        int maxAge = 100;
        if(age.getYears() <= minAge || age.getYears() >= maxAge){
            throw new ValidationException("Enter valid dob");
        }

        //validate gender
        String gender=user.getGender();
        if(gender==null){
            throw new ValidationException("Enter gender");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        User addUser=new User();
        addUser.setEmail(user.getEmail());
        addUser.setFirstName(user.getFirstName());
        addUser.setLastName(user.getLastName());
        addUser.setPassword(user.getPassword());
        addUser.setDob(user.getDob());
        addUser.setGender(user.getGender());
        addUser.setRoles(user.getRoles());
        addUser.setResetCode(null);
        addUser.setExpiration(null);
        try {
            userDao.save(addUser);
            return "User added Successfully";
        } catch (Exception ex) {
            // Handle any other exceptions that may occur during the database operation
            throw new ValidationException("An error occurred while saving the user.");
        }
    }
    @Override
    public String userLogin(JwtRequestModel userLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(userLogin.getUsername());
        } else {
            throw new ValidationException("Password does not match");
        }
    }

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> get1User(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public UUID generateResetCode() {
        return UUID.randomUUID();
    }

    @Override
    public void generateAndSendResetCode(String email) {
        User user = findUserByEmail(email);
        if (user != null) {
            UUID resetCode = generateResetCode();
            user.setResetCode(resetCode);
            // Set an expiration time for the reset code (e.g., 1 hour from now)
            user.setExpiration(LocalDateTime.now().plusHours(1));
            userDao.save(user);
            sendResetEmail(email, resetCode);
        }
    }

    @Override
    public void sendResetEmail(String email, UUID resetCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset");
        message.setText("Your reset code is: " + resetCode.toString());
        javaMailSender.send(message);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email).orElse(null);
    }

    @Override
    public boolean isResetCodeValid(String email, String resetCode) {
        User user = findUserByEmail(email);
        if (user != null && user.getResetCode().toString().equals(resetCode)) {
            LocalDateTime expiration = user.getExpiration();
            return expiration != null && expiration.isAfter(LocalDateTime.now());
        }
        return false;
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        User user = findUserByEmail(email);
        if (user != null) {
            String password=user.getPassword();
            if(password.length()<8){
                throw new ValidationException("password should contain more than 8 characters");
            }
            if(!password.matches(".*[A-Z].*")){
                throw new ValidationException("password should contain at least 1 uppercase");
            }
            if(!password.matches(".*[a-z].*")){
                throw new ValidationException("password should contain at least 1 lowercase");
            }
            if(!password.matches(".*\\d.*")){
                throw new ValidationException("password should contain at least 1 digit");
            }
            if(!password.matches(".*[!@#$%^&*()_+{}|:\"<>?~].*")){
                throw new ValidationException("password should contain at least 1 special character");
            }
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            user.setResetCode(null);
            user.setExpiration(null);
            userDao.save(user);
        }
    }
    @Override
    public Optional<User> getCurrentUser(UserDetails userDetails) {
        if (userDetails != null && userDetails.getUsername() != null) {
            Optional<User> userOptional = userDao.findByEmail(userDetails.getUsername());
            return Optional.ofNullable(userOptional.orElse(null)); // Return the User or null if it doesn't exist
        }
        return null;
    }

    @Override
    public void updateUserProfile(Optional<User> user, UserProfile userProfile) {
        if (user.isPresent()) {
            User actualUser = user.get();
            actualUser.setPrivate_acc(userProfile.isPrivate_acc());
            actualUser.setFullName(userProfile.getFullName());
            actualUser.setBio(userProfile.getBio());
            actualUser.setProfilePhoto(userProfile.getProfilePhoto());
            userDao.save(actualUser);
        }
    }

    @Override
    public ResponseEntity<Object> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String refreshToken = null;
            String email = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                refreshToken = authHeader.substring(7);
                email = jwtService.extractUsername(refreshToken);
            }

            if (email != null) {
                User user =userDao.findByEmail(email).orElse(null);
                UserDetails userDetails=Optional.ofNullable(user)
                        .map(UserInfoUserDetails::new)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + user));
                if (jwtService.validateToken(refreshToken, userDetails)) {
                    String accessToken = jwtService.generateToken(user.getEmail());
                    Map<String, Object> loginResponse = new HashMap<>();
                    loginResponse.put("accessToken", accessToken);
                    loginResponse.put("refreshToken", refreshToken);
                    Map<String,Map<String,Object>> map=new HashMap<>();
                    map.put("token refreshed",loginResponse);
                    return ResponseEntity.ok(map);
                }
            }
            return ResponseEntity.badRequest().body("login again");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}