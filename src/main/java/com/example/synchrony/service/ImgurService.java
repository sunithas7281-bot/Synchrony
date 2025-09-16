package com.example.synchrony.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;

import java.util.Map;

@Service
public class ImgurService {
    private final RestTemplate rest = new RestTemplate();

    @Value("${imgur.clientId}")
    private String clientId;

    public record ImgurUpload(String id, String link, String deletehash) {}

    public ImgurUpload upload(MultipartFile file) {
        try {
            String url = "https://api.imgur.com/3/image";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("Authorization", "Client-ID " + clientId);

            ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                @Override public String getFilename() { return file.getOriginalFilename(); }
            };

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", resource);

            HttpEntity<MultiValueMap<String, Object>> req = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = rest.postForEntity(url, req, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Map data = (Map) response.getBody().get("data");
                String id = String.valueOf(data.get("id"));
                String link = String.valueOf(data.get("link"));
                String deletehash = String.valueOf(data.get("deletehash"));
                return new ImgurUpload(id, link, deletehash);
            }
            throw new RuntimeException("Imgur upload failed: " + response.getStatusCode());
        } catch (Exception e) {
            throw new RuntimeException("Imgur upload error", e);
        }
    }

    public void deleteByDeleteHash(String deleteHash) {
        String url = "https://api.imgur.com/3/image/" + deleteHash;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Client-ID " + clientId);
        HttpEntity<Void> req = new HttpEntity<>(headers);
        ResponseEntity<Map> res = rest.exchange(url, HttpMethod.DELETE, req, Map.class);
        if (!res.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Imgur delete failed: " + res.getStatusCode());
        }
    }
}
