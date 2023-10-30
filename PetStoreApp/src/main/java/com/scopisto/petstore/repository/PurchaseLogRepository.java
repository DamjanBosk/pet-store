package com.scopisto.petstore.repository;

import com.scopisto.petstore.model.PurchaseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseLogRepository extends JpaRepository<PurchaseLog, Long> {
}
