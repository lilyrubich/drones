package com.musalasoft.drones.repository;

import com.musalasoft.drones.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

    @Query(nativeQuery = true, value = "select * from drone where state in(:states)")
    List<Drone> findDronesByState(List<String> states);

    @Query(nativeQuery = true, value = "select battery_capacity from drone where drone")
    Float checkDroneBatteryLevel(Long droneId);

}
