package com.example.synchrony.controller;

import com.example.synchrony.dto.ImageResponse;
import com.example.synchrony.entity.ImageRecord;
import com.example.synchrony.entity.UserAccount;
import com.example.synchrony.service.ImageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final ImageService images;
    public ImageController(ImageService images) { this.images = images; }

    @PostMapping
    public ImageResponse upload(@AuthenticationPrincipal UserAccount user,
                                @RequestParam("file") MultipartFile file) {
        ImageRecord rec = images.upload(user, file);
        return new ImageResponse(rec.getId(), rec.getImgurId(), rec.getLink(), rec.getCreatedAt());
    }

    @GetMapping
    public List<ImageResponse> list(@AuthenticationPrincipal UserAccount user) {
        return images.list(user).stream()
                .map(r -> new ImageResponse(r.getId(), r.getImgurId(), r.getLink(), r.getCreatedAt()))
                .toList();
    }

    @DeleteMapping("/{id}")
    public void delete(@AuthenticationPrincipal UserAccount user, @PathVariable Long id) {
        images.delete(user, id);
    }
}
