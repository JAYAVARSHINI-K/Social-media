package com.example.socialmedia.controller;

import com.example.socialmedia.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/friends")
public class FriendsController {
    @Autowired
    private FriendsService friendsService;

    @GetMapping("/getFriends/{userEmail}")
    public ResponseEntity<Object> getFriends(@PathVariable String userEmail){
        return ResponseEntity.ok(friendsService.getFriends(userEmail));
    }

}
