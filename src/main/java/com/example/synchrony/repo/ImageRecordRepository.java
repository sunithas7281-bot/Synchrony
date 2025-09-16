package com.example.synchrony.repo;

import com.example.synchrony.entity.ImageRecord;
import com.example.synchrony.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageRecordRepository extends JpaRepository<ImageRecord, Long> {
    List<ImageRecord> findByOwnerOrderByCreatedAtDesc(UserAccount owner);
}
