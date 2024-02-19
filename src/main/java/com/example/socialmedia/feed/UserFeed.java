package com.example.socialmedia.feed;

import com.example.socialmedia.model.User;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table
public class UserFeed implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    private String title;
    private String description;
    private String imageUrl;
    @ElementCollection
    private Set<String> tags;
    private String category;
    private LocalDateTime timestamp;
    @Column(columnDefinition = "boolean default false")
    private boolean archive;

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public UserFeed() {
    }

    public UserFeed(User user, String title, String description, String imageUrl, Set<String> tags, String category, LocalDateTime timestamp) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.category = category;
        this.timestamp = timestamp;
    }

    public UserFeed(String title, String description, String imageUrl, Set<String> tags, String category, LocalDateTime timestamp) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.category = category;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UserFeed{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", tags=" + tags +
                ", category='" + category + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
