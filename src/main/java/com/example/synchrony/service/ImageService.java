package com.example.synchrony.service;

import com.example.synchrony.entity.ImageRecord;
import com.example.synchrony.entity.UserAccount;
import com.example.synchrony.events.ImageUploadedEvent;
import com.example.synchrony.repo.ImageRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageService {
    private final ImageRecordRepository repo;
    private final ImgurService imgur;
    private final ImageEventPublisher publisher;   // <-- add

    public ImageService(ImageRecordRepository repo, ImgurService imgur, ImageEventPublisher publisher) {
        this.repo = repo;
        this.imgur = imgur;
        this.publisher = publisher;
    }

    @Transactional
    public ImageRecord upload(UserAccount user, MultipartFile file) {
        var up = imgur.upload(file);
        ImageRecord rec = new ImageRecord();
        rec.setOwner(user);
        rec.setImgurId(up.id());
        rec.setLink(up.link());
        rec.setDeleteHash(up.deletehash());
        rec = repo.save(rec);

        // fire-and-forget event (don’t fail the request if Kafka is down)
        try {
            publisher.publish(new ImageUploadedEvent(
                user.getId(), user.getUsername(), rec.getId(), rec.getImgurId(), rec.getLink(), rec.getCreatedAt()
            ));
        } catch (Exception ignore) {
            // optionally log a warning here
        }
        return rec;
    }

    public List<ImageRecord> list(UserAccount user) {
        return repo.findByOwnerOrderByCreatedAtDesc(user);
    }

    @Transactional
    public void delete(UserAccount user, Long id) {
        ImageRecord rec = repo.findById(id).orElseThrow();
        if (!rec.getOwner().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Not your image");
        }
        imgur.deleteByDeleteHash(rec.getDeleteHash());
        repo.delete(rec);
    }
}
