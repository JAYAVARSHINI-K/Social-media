package com.example.socialmedia.controller;

import com.example.socialmedia.feed.FeedDto;
import com.example.socialmedia.model.User;
import com.example.socialmedia.service.FeedService;
import com.example.socialmedia.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    private FeedService feedService;

    @Autowired
    private UserServices userService;

    @PostMapping("/saveFeed/{feeding_user}")
    public ResponseEntity<Object> saveFeed(@RequestBody FeedDto feed, @PathVariable String feeding_user){
        return ResponseEntity.ok(feedService.saveFeed(feed,feeding_user));
    }

    @GetMapping("/friendsFeeds")
//    @Cacheable(value = "feeds", key = "#userDetails.username")
    public ResponseEntity<Object> friendsFeeds(@AuthenticationPrincipal UserDetails userDetails){
        Optional<User> user = userService.getCurrentUser(userDetails);
        if(user.isPresent()) {
            return ResponseEntity.ok(feedService.friendsFeeds(user.get().getEmail()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/publicFeeds")
//    @Cacheable(value = "feeds", key = "'public-feeds'")
    public ResponseEntity<Object> publicFeeds(){
        return ResponseEntity.ok(feedService.publicFeeds());
    }

    @PutMapping("/deleteFeed/{id}")
    @CacheEvict(value = "feeds", key = "#id")
    public ResponseEntity<Object> deleteFeed(@PathVariable Long id){
        return ResponseEntity.ok(feedService.deleteFeed(id));
    }
}
