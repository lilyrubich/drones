package com.musalasoft.drones.service;

import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.enums.DroneState;
import com.musalasoft.drones.repository.DroneRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DroneService {

    private final Logger logger = LoggerFactory.getLogger(DroneService.class);
    private final DroneRepository droneRepository;

    public DroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    //register a drone
    public Drone save(Drone drone) {
        Drone response = droneRepository.save(drone);
        logger.info("New drone has registered: " + drone);
        return response;
    }

    //check available drones for loading
    public List<Drone> getAvailableDronesForLoading() {
        List<String> states = List.of(DroneState.IDLE.name(), DroneState.LOADING.name(), DroneState.LOADED.name());
        return droneRepository.findDronesByState(states);
    }

    //check drone battery level for a given drone
    public Float checkDroneBatteryLevel(Long droneId) throws IllegalArgumentException {
        Optional<Drone> drone = droneRepository.findById(droneId);
        if (drone.isPresent())
            return drone.get().getBatteryCapacity();
        else
            throw new IllegalArgumentException("Drone with droneId = " + droneId + " doesn't exist");
    }

    @Transactional
    public List<Drone> saveAll(List<Drone> drones) {
        List<Drone> response = droneRepository.saveAll(drones);
        logger.info("New drones have registered: " + drones);
        return response;
    }

}
