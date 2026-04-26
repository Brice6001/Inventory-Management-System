package com.airtel.inventory.repository;

import com.airtel.inventory.entity.ConditionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionLogRepository extends JpaRepository<ConditionLog, Long> {
}