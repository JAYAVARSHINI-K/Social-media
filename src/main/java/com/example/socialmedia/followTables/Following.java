package com.example.socialmedia.followTables;

import com.example.socialmedia.model.User;
import jakarta.persistence.*;

@Entity
@Table
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long followingId;
    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    public Following() {
    }

    public Following(long followingId, User user1, User user2) {
        this.followingId = followingId;
        this.user1 = user1;
        this.user2 = user2;
    }

    public long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(long followingId) {
        this.followingId = followingId;
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
                "followersId=" + followingId +
                ", user1='" + user1 + '\'' +
                ", user2='" + user2 + '\'' +
                '}';
    }
}
