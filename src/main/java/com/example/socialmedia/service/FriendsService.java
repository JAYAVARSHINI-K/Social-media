package com.example.socialmedia.service;

import com.example.socialmedia.Repository.FriendsListRepo;
import com.example.socialmedia.Repository.UserRepository;
import com.example.socialmedia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendsListRepo friendsListRepo;

    public Object getFriends(String userEmail) {
        User user=userRepository.findByEmail(userEmail).orElse(null);
        return friendsListRepo.findByUser(user);
    }
}
