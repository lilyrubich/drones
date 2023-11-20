package com.musalasoft.drones.repository;

import com.musalasoft.drones.entity.CheckBatteryTaskLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckBatteryTaskLogRepository extends JpaRepository<CheckBatteryTaskLog, Long> {
}
