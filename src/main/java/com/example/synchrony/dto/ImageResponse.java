package com.example.synchrony.dto;

import java.time.Instant;

public record ImageResponse(Long id, String imgurId, String link, Instant createdAt) {}
