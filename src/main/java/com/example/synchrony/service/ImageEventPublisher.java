package com.example.synchrony.service;

import com.example.synchrony.events.ImageUploadedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ImageEventPublisher {
    private final KafkaTemplate<String, ImageUploadedEvent> kafka;
    private final String topic;

    public ImageEventPublisher(KafkaTemplate<String, ImageUploadedEvent> kafka,
                               @Value("${app.kafka.topic:image-uploads}") String topic) {
        this.kafka = kafka;
        this.topic = topic;
    }

    public void publish(ImageUploadedEvent evt) {
        // use username as the key for stable partitioning
        kafka.send(topic, evt.getUsername(), evt);
    }
}
