package com.example.synchrony.events;

import java.time.Instant;

public class ImageUploadedEvent {
    private Long userId;
    private String username;
    private Long imageId;       // internal DB id
    private String imgurId;     // Imgur id
    private String link;        // Imgur link
    private Instant createdAt;

    public ImageUploadedEvent() {}

    public ImageUploadedEvent(Long userId, String username, Long imageId, String imgurId, String link, Instant createdAt) {
        this.userId = userId;
        this.username = username;
        this.imageId = imageId;
        this.imgurId = imgurId;
        this.link = link;
        this.createdAt = createdAt;
    }

    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public Long getImageId() { return imageId; }
    public String getImgurId() { return imgurId; }
    public String getLink() { return link; }
    public Instant getCreatedAt() { return createdAt; }

    public void setUserId(Long userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setImageId(Long imageId) { this.imageId = imageId; }
    public void setImgurId(String imgurId) { this.imgurId = imgurId; }
    public void setLink(String link) { this.link = link; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
