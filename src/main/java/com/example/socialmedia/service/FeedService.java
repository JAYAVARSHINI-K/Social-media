package com.example.socialmedia.service;

import com.example.socialmedia.feed.FeedResponseDTO;
import com.example.socialmedia.Repository.FeedRepo;
import com.example.socialmedia.Repository.FollowersRepo;
import com.example.socialmedia.Repository.UserRepository;
import com.example.socialmedia.feed.FeedDto;
import com.example.socialmedia.feed.UserFeed;
import com.example.socialmedia.followTables.Followers;
import com.example.socialmedia.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedService {

    @Autowired
    private FeedRepo feedRepo;

    @Autowired
    private FollowersRepo followersRepo;

    @Autowired
    private UserRepository userRepository;

    public String saveFeed(FeedDto feed, String feedingUser_email) {
        UserFeed userFeed=new UserFeed();
        userFeed.setUser(userRepository.findByEmail(feedingUser_email).orElse(null));
        userFeed.setTitle(feed.getTitle());
        userFeed.setDescription(feed.getDescription());
        userFeed.setImageUrl(feed.getImageUrl());
        userFeed.setTags(feed.getTags());
        userFeed.setCategory(feed.getCategory());
        userFeed.setTimestamp(LocalDateTime.now());
        feedRepo.save(userFeed);
        return "Feed saved";
    }

    @Cacheable(value = "feeds", key = "#getAuthenticatedUsername()")
    public Object friendsFeeds(String email) {
        User user=userRepository.findByEmail(email).orElse(null);
        List<Followers> followers=followersRepo.findAllByUser1(user);
        List<User> friends=new ArrayList<>();
        for(Followers follower:followers){
            friends.add(follower.getUser2());
        }
        List<FeedResponseDTO> feedResponseDTO=new ArrayList<>();
        List<UserFeed> friendsFeeds=feedRepo.findAllByUserInAndArchiveFalse(friends);
        for (UserFeed userFeed:friendsFeeds) {
            FeedResponseDTO feeds = new FeedResponseDTO();
            feeds.setId(userFeed.getId());
            feeds.setUser(userFeed.getUser().getEmail());
            feeds.setTitle(userFeed.getTitle());
            feeds.setCategory(userFeed.getCategory());
            feeds.setTags(userFeed.getTags());
            feeds.setDescription(userFeed.getDescription());
            feeds.setImageUrl(userFeed.getImageUrl());
            feeds.setTimestamp(userFeed.getTimestamp());
            feedResponseDTO.add(feeds);
        }
        return feedResponseDTO;
    }

    @Cacheable(value = "feeds", key = "'public-feeds'")
    public Object publicFeeds() {
        List<User> publicUsers=userRepository.findAllByPrivateAccFalse();
        List<FeedResponseDTO> feedResponseDTO=new ArrayList<>();
        List<UserFeed> publicFeeds=feedRepo.findAllByUserInAndArchiveFalse(publicUsers);
        for (UserFeed userFeed:publicFeeds) {
            FeedResponseDTO feeds = new FeedResponseDTO();
            feeds.setId(userFeed.getId());
            feeds.setUser(userFeed.getUser().getEmail());
            feeds.setTitle(userFeed.getTitle());
            feeds.setCategory(userFeed.getCategory());
            feeds.setTags(userFeed.getTags());
            feeds.setDescription(userFeed.getDescription());
            feeds.setImageUrl(userFeed.getImageUrl());
            feeds.setTimestamp(userFeed.getTimestamp());
            feedResponseDTO.add(feeds);
        }
        return feedResponseDTO;
    }

    public Object deleteFeed(Long id) {
        UserFeed feed=feedRepo.findById(id).orElse(null);
        if(feed!=null) {
            feed.setArchive(true);
            feedRepo.save(feed);
        }
        return "Archived";
    }
}