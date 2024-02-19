package com.example.socialmedia.Repository;

import com.example.socialmedia.feed.UserFeed;
import com.example.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepo extends JpaRepository<UserFeed,Long> {
    List<UserFeed> findAllByUserIn(List<User> publicUsers);
    List<UserFeed> findAllByUserInAndArchiveFalse(List<User> users);

}
