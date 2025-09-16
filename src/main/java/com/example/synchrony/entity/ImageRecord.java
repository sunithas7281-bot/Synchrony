package com.example.synchrony.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "images")
public class ImageRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private UserAccount owner;

    private String imgurId;
    private String link;
    private String deleteHash;

    private Instant createdAt = Instant.now();

    public ImageRecord() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserAccount getOwner() { return owner; }
    public void setOwner(UserAccount owner) { this.owner = owner; }

    public String getImgurId() { return imgurId; }
    public void setImgurId(String imgurId) { this.imgurId = imgurId; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getDeleteHash() { return deleteHash; }
    public void setDeleteHash(String deleteHash) { this.deleteHash = deleteHash; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
