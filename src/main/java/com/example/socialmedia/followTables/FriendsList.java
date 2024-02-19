package com.example.socialmedia.followTables;

import com.example.socialmedia.model.User;
import jakarta.persistence.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "friends")
public class FriendsList {
    @Id
    private String id;
    private User user;
    private List<User> friends;

    public FriendsList() {
    }

    public FriendsList(String id, User user, List<User> friends) {
        this.id = id;
        this.user = user;
        this.friends = friends;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
