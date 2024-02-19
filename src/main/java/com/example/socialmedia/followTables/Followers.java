package com.example.socialmedia.followTables;

import com.example.socialmedia.model.User;
import jakarta.persistence.*;

@Entity
@Table
public class Followers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long followersId;
    @ManyToOne
    private User user1;

    //mapped to
    @ManyToOne
    private User user2;

    public Followers() {
    }

    public Followers(long followersId, User user1, User user2) {
        this.followersId = followersId;
        this.user1 = user1;
        this.user2 = user2;
    }

    public long getFollowersId() {
        return followersId;
    }

    public void setFollowersId(long followersId) {
        this.followersId = followersId;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    @Override
    public String toString() {
        return "Followers{" +
                "followersId=" + followersId +
                ", user1='" + user1 + '\'' +
                ", user2='" + user2 + '\'' +
                '}';
    }
}
