package com.musalasoft.drones.service;

import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.enums.DroneModel;
import com.musalasoft.drones.enums.DroneState;
import com.musalasoft.drones.repository.DroneRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;


public class DroneServiceTest {

    private DroneRepository droneRepository;
    private DroneService droneService;

    @Before
    public void init() {
        droneRepository = Mockito.mock(DroneRepository.class);
        droneService = new DroneService(droneRepository);
    }


    //check available drones for loading test1
    @Test
    public void should_FindDronesForLoading_IfAvailableDronesExist() {
        List<String> availableDroneStates;
        availableDroneStates = List.of(DroneState.IDLE.name(), DroneState.LOADING.name(), DroneState.LOADED.name());
        List<Drone> drones = List.of(
                new Drone(1L, "AUTOTEST_DRONE1", (float) 0.4, DroneState.LOADED, DroneModel.Heavyweight),
                new Drone(1L, "AUTOTEST_DRONE2", (float) 0.5, DroneState.LOADING, DroneModel.Lightweight),
                new Drone(1L, "AUTOTEST_DRONE3", (float) 0.6, DroneState.IDLE, DroneModel.Middleweight)
        );

        Mockito.when(droneRepository.findDronesByState(availableDroneStates)).thenReturn(drones);
        Assert.assertEquals(drones, droneService.getAvailableDronesForLoading());
    }

    //check available drones for loading test2
    @Test
    public void should_ReturnEmptyResponse_IfAvailableDronesDoesNotExist() {
        List<String> availableDroneStates;
        availableDroneStates = List.of(DroneState.IDLE.name(), DroneState.LOADING.name(), DroneState.LOADED.name());
        Mockito.when(droneRepository.findDronesByState(availableDroneStates)).thenReturn(null);
        Assert.assertEquals(null, droneService.getAvailableDronesForLoading());
    }

    //check drone battery level for a given drone test1
    @Test
    public void should_ReturnBatteryLevel_IfDroneIsCorrect() {
        Mockito.when(droneRepository.findById(1L)).thenReturn(Optional.of(new Drone(1L, "AUTOTEST_DRONE1", (float) 0.55, DroneState.LOADED, DroneModel.Heavyweight)));
        Mockito.when(droneRepository.checkDroneBatteryLevel(1L)).thenReturn((float) 0.55);
        Assert.assertEquals(Float.valueOf((float) 0.55), droneService.checkDroneBatteryLevel(1L));
    }

    //check drone battery level for a given drone test2
    @Test(expected = IllegalArgumentException.class)
    public void should_ReturnIllegalArgumentException_IfDroneIsNotCorrect() {
        Mockito.when(droneRepository.findById(1L)).thenReturn(Optional.empty());
        droneService.checkDroneBatteryLevel(1L);
    }

    //register a drone test 1
    @Test
    public void should_ReturnRegisteredDrone_IfDroneIsValid() {
        Drone drone = new Drone(1L, "AUTOTEST_DRONE1", (float) 0.4, DroneState.LOADED, DroneModel.Heavyweight);
        Mockito.when(droneRepository.save(drone)).thenReturn(drone);
        Assert.assertEquals(drone, droneService.save(drone));
    }

    //register a list of drones test 1
    @Test
    public void should_ReturnRegisteredDrones_IfDronesIsValid() {
        List<Drone> drones = List.of(
                new Drone(1L, "AUTOTEST_DRONE1", (float) 0.4, DroneState.LOADED, DroneModel.Heavyweight),
                new Drone(1L, "AUTOTEST_DRONE2", (float) 0.5, DroneState.LOADING, DroneModel.Lightweight),
                new Drone(1L, "AUTOTEST_DRONE3", (float) 0.6, DroneState.IDLE, DroneModel.Middleweight)
        );

        Mockito.when(droneRepository.saveAll(drones)).thenReturn(drones);
        Assert.assertEquals(drones, droneService.saveAll(drones));
    }
}
