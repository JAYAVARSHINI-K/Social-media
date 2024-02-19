package com.example.socialmedia.followTables;

import com.example.socialmedia.model.User;
import jakarta.persistence.*;


@Entity
@Table
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long requestId;
    @ManyToOne
    private User user1; //requested to
    @ManyToOne
    private User user2; //request from

    public Request() {
    }

    public Request(long requestId, User user1, User user2) {
        this.requestId = requestId;
        this.user1 = user1;
        this.user2 = user2;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
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
        return "Request{" +
                "requestId=" + requestId +
                ", user1='" + user1 + '\'' +
                ", user2='" + user2 + '\'' +
                '}';
    }
}
