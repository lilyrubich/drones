package com.musalasoft.drones.task;

import com.musalasoft.drones.entity.CheckBatteryTaskLog;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.repository.CheckBatteryTaskLogRepository;
import com.musalasoft.drones.repository.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class BatteryCheckerTask {
    private final Logger logger = LoggerFactory.getLogger(BatteryCheckerTask.class);
    private final CheckBatteryTaskLogRepository auditRepo;
    private final DroneRepository droneRepository;

    public BatteryCheckerTask(CheckBatteryTaskLogRepository auditRepo, DroneRepository droneRepository) {
        this.auditRepo = auditRepo;
        this.droneRepository = droneRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    private void checkBattery() {
        logger.info("Start checking drones battery capacity");
        LocalDateTime dateTime = LocalDateTime.now();
        List<Drone> all = droneRepository.findAll();
        List<CheckBatteryTaskLog> records = new ArrayList<>();
        for (Drone drone : all) {
            records.add(new CheckBatteryTaskLog(dateTime, drone.getId(), drone.getBatteryCapacity()));
        }
        auditRepo.saveAll(records);
        logger.info("End checking drones battery capacity");
    }
}
