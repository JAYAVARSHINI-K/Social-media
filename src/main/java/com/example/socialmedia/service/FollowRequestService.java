package com.example.socialmedia.service;

import com.example.socialmedia.Repository.*;
import com.example.socialmedia.followTables.Followers;
import com.example.socialmedia.followTables.Following;
import com.example.socialmedia.followTables.FriendsList;
import com.example.socialmedia.followTables.Request;
import com.example.socialmedia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FollowRequestService {
    @Autowired
    private RequestRepo requestRepo;

    @Autowired
    private FollowingRepo followingRepo;

    @Autowired
    private FollowersRepo followersRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendsListRepo friendsListRepo;

    private Request request;
    private Following following;
    private Followers followers;


    public String request(String requester1, String receiver1) {
        User requester =userRepository.findByEmail(requester1).orElse(null);
        User receiver =userRepository.findByEmail(receiver1).orElse(null);
        if(receiver!=null){
        if(receiver.isPrivate_acc()) {
            request = requestRepo.findByUser1AndUser2(requester, receiver);
            if (request == null) {
                request = new Request();
                request.setUser1(receiver);
                request.setUser2(requester);
                requestRepo.save(request);
            }
        }
            return "Requested";
        }
        else{
            return accept(requester1,receiver1);
        }
    }

    public String accept(String requester1, String receiver1) {
        User requester =userRepository.findByEmail(requester1).orElse(null);
        User receiver =userRepository.findByEmail(receiver1).orElse(null);
        request=requestRepo.findByUser1AndUser2(receiver,requester);
        if(request!=null){
            requestRepo.delete(request);
        }


        followers=followersRepo.findByUser1AndUser2(receiver,requester);
        if (followers==null)
        {
            followers=new Followers();
            followers.setUser1(receiver);
            followers.setUser2(requester);
            followersRepo.save(followers);
        }

        following=followingRepo.findByUser1AndUser2(requester,receiver);
        if (following==null)
        {
            following=new Following();
            following.setUser1(requester);
            following.setUser2(receiver);
            followingRepo.save(following);
        }

        FriendsList friendsList=friendsListRepo.findByUser(requester);
        if (friendsList==null){
            friendsList.setId(UUID.randomUUID().toString().split("-")[0]);
            FriendsList friendsList1=new FriendsList();
            friendsList1.setUser(receiver);
            friendsList1.setFriends(List.of(requester));
            friendsListRepo.save(friendsList1);
        }
        else {
            List<User> friends=friendsList.getFriends();
            friends.add(requester);
            friendsList.setFriends(friends);
            friendsListRepo.save(friendsList);
        }

        return "Following";
    }

    public String reject(String requester1, String receiver1) {
        User requester =userRepository.findByEmail(requester1).orElse(null);
        User receiver =userRepository.findByEmail(receiver1).orElse(null);
        request=requestRepo.findByUser1AndUser2(receiver,requester);
        if (request!=null){
            requestRepo.delete(request);
        }

        return "Rejected";
    }

    public String unfollow(String requester1, String receiver1) {
        User requester =userRepository.findByEmail(requester1).orElse(null);
        User receiver =userRepository.findByEmail(receiver1).orElse(null);
        followers=followersRepo.findByUser1AndUser2(receiver,requester);
        if (followers!=null) {
            followersRepo.delete(followers);
        }

        following=followingRepo.findByUser1AndUser2(requester,receiver);
        if (following!=null) {
            followingRepo.delete(following);
        }

        return "UnFollowed";
    }

    public List<Followers> followers(String user1) {
        User user=userRepository.findByEmail(user1).orElse(null);
        if (user!=null) {
            return followersRepo.findAllByUser1(user);
        }
        return new ArrayList<>();
    }

    public List<Following> following(String user1) {
        User user=userRepository.findByEmail(user1).orElse(null);
        if (user!=null) {
            return followingRepo.findAllByUser1(user);
        }
        return new ArrayList<>();
    }
}
