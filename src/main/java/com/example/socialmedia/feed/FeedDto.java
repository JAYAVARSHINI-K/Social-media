package com.example.socialmedia.feed;

import java.util.Set;

public class FeedDto{
    private String title;
    private String description;
    private String imageUrl;
    private Set<String> tags;
    private String category;

    public FeedDto() {
    }

    public FeedDto(String title, String description, String imageUrl, Set<String> tags, String category) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.category = category;
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


    @Override
    public String toString() {
        return "feedDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", tags=" + tags +
                ", category='" + category + '\'' +
                '}';
    }
}
