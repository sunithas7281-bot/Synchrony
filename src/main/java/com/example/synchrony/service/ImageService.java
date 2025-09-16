package com.example.synchrony.service;

import com.example.synchrony.entity.ImageRecord;
import com.example.synchrony.entity.UserAccount;
import com.example.synchrony.repo.ImageRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageService {
    private final ImageRecordRepository repo;
    private final ImgurService imgur;

    public ImageService(ImageRecordRepository repo, ImgurService imgur) {
        this.repo = repo; this.imgur = imgur;
    }

    @Transactional
    public ImageRecord upload(UserAccount user, MultipartFile file) {
        var up = imgur.upload(file);
        ImageRecord rec = new ImageRecord();
        rec.setOwner(user);
        rec.setImgurId(up.id());
        rec.setLink(up.link());
        rec.setDeleteHash(up.deletehash());
        return repo.save(rec);
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
