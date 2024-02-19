package com.example.socialmedia.controller;

import com.example.socialmedia.service.FollowRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/follow")
public class FollowRequestController {

    @Autowired
    private FollowRequestService followRequestService;


    @PostMapping("/request/{requester}/{receiver}")
    public ResponseEntity<Object> request(@PathVariable String requester,@PathVariable String receiver){
        return ResponseEntity.ok(followRequestService.request(requester,receiver));
    }

    @PostMapping("/accept/{requester}/{receiver}")
    public ResponseEntity<Object> accept(@PathVariable String requester,@PathVariable String receiver){
        return ResponseEntity.ok(followRequestService.accept(requester,receiver));
    }

    @DeleteMapping("/reject/{requester}/{receiver}")
    public ResponseEntity<Object> reject(@PathVariable String requester,@PathVariable String receiver){
        return ResponseEntity.ok(followRequestService.reject(requester,receiver));
    }

    @DeleteMapping("/unfollow/{requester}/{receiver}")
    public ResponseEntity<Object> unfollow(@PathVariable String requester,@PathVariable String receiver){
        return ResponseEntity.ok(followRequestService.unfollow(requester,receiver));
    }

    @GetMapping("followers/{user}")
    public ResponseEntity<Object> followers(@PathVariable String user){
        return ResponseEntity.ok(followRequestService.followers(user));
    }

    @GetMapping("following/{user}")
    public ResponseEntity<Object> following(@PathVariable String user){
        return ResponseEntity.ok(followRequestService.following(user));
    }
}