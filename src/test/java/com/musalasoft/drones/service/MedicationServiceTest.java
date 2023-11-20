package com.musalasoft.drones.service;

import com.musalasoft.drones.body.LoadingDroneRequestBody;
import com.musalasoft.drones.body.MedicationResponseBody;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.enums.DroneModel;
import com.musalasoft.drones.enums.DroneState;
import com.musalasoft.drones.repository.DroneRepository;
import com.musalasoft.drones.repository.MedicationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.naming.LimitExceededException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicationServiceTest {

    private MedicationRepository medicationRepository;
    private DroneRepository droneRepository;
    private MedicationService medicationService;

    @Before
    public void init() {
        medicationRepository = Mockito.mock(MedicationRepository.class);
        droneRepository = Mockito.mock(DroneRepository.class);
        medicationService = new MedicationService(medicationRepository, droneRepository);
    }

    //loading a drone with medication items test1
    @Test
    public void should_LoadingDroneWithMedications_IfDroneIsAvailable() throws LimitExceededException {
        Drone drone = new Drone(1L, "AUTOTEST_DRONE1", (float) 0.9, DroneState.IDLE, DroneModel.Heavyweight);
        LoadingDroneRequestBody requestBody = new LoadingDroneRequestBody(drone.getId(), List.of(1L, 2L));

        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication(1L, "AutoTestMedication1", 100, "TEST-1", new byte[]{0x02, 0x00, 0x00, 0x00, 0x45, 0x00, 0x05}, null));
        medications.add(new Medication(2L, "AutoTestMedication2", 200, "TEST-2", new byte[]{0x02, 0x05, 0x05, 0x00, 0x45, 0x00, 0x00}, null));

        List<MedicationResponseBody> expectedResponse = new ArrayList<>();
        expectedResponse.add(MedicationResponseBody.getBuilder()
                .setId(1L)
                .setName("AutoTestMedication1")
                .setWeight(100)
                .setCode("TEST-1")
                .setImage(new byte[]{0x02, 0x00, 0x00, 0x00, 0x45, 0x00, 0x05})
                .setDroneId(drone.getId())
                        .build());
        expectedResponse.add(MedicationResponseBody.getBuilder()
                .setId(2L)
                .setName("AutoTestMedication2")
                .setWeight(200)
                .setCode("TEST-2")
                .setImage(new byte[]{0x02, 0x05, 0x05, 0x00, 0x45, 0x00, 0x00})
                .setDroneId(drone.getId())
                        .build());

        Mockito.when(droneRepository.findById(requestBody.getDroneId())).thenReturn(Optional.of(drone));
        Mockito.when(medicationRepository.findMedicationsByIds(requestBody.getMedicationIds())).thenReturn(medications);
        Mockito.when(droneRepository.getReferenceById(drone.getId())).thenReturn(drone);

        List<Medication> oldMedications = new ArrayList<>();
        Mockito.when(medicationRepository.saveAndFlush(medications.get(0))).thenReturn(medications.get(0));
        Mockito.when(medicationRepository.saveAndFlush(medications.get(1))).thenReturn(medications.get(1));
        Mockito.when(medicationRepository.findMedicationsByDroneId(drone.getId())).thenReturn(oldMedications);

        List<MedicationResponseBody> medicationResponseBodies = medicationService.loadingDroneWithMedications(requestBody);
        Assert.assertEquals(drone.getId(), medicationResponseBodies.get(0).getDroneId());
        Assert.assertEquals(drone.getId(), medicationResponseBodies.get(1).getDroneId());
    }

    //loading a drone with medication items test2
    @Test(expected = IllegalStateException.class)
    public void should_ReturnIllegalStateException_IfDroneDoesNotChargedEnough() throws LimitExceededException {
        Drone drone = new Drone(1L, "AUTOTEST_DRONE1", (float) 0.24, DroneState.IDLE, DroneModel.Heavyweight);
        LoadingDroneRequestBody requestBody = new LoadingDroneRequestBody(drone.getId(), List.of(1L));

        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication(1L, "AutoTestMedication1", 100, "TEST-1", new byte[]{0x02, 0x00, 0x00, 0x00, 0x45, 0x00, 0x05}, null));

        Mockito.when(medicationRepository.findMedicationsByIds(requestBody.getMedicationIds())).thenReturn(medications);
        Mockito.when(droneRepository.getReferenceById(drone.getId())).thenReturn(drone);
        medicationService.loadingDroneWithMedications(requestBody);
    }

    ////loading a drone with medication items test3
    @Test(expected = IllegalStateException.class)
    public void should_ReturnIllegalStateException_IfDroneUnavailableForLoading() throws LimitExceededException {
        Drone drone = new Drone(1L, "AUTOTEST_DRONE1", (float) 0.5, DroneState.DELIVERING, DroneModel.Heavyweight);
        LoadingDroneRequestBody requestBody = new LoadingDroneRequestBody(drone.getId(), List.of(1L));

        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication(1L, "AutoTestMedication1", 100, "TEST-1", new byte[]{0x02, 0x00, 0x00, 0x00, 0x45, 0x00, 0x05}, null));

        Mockito.when(medicationRepository.findMedicationsByIds(requestBody.getMedicationIds())).thenReturn(medications);
        Mockito.when(droneRepository.getReferenceById(drone.getId())).thenReturn(drone);
        medicationService.loadingDroneWithMedications(requestBody);
    }

    //loading a drone with medication items test4
    @Test(expected = LimitExceededException.class)
    public void should_ReturnLimitExceededException_IfDroneWeightLimitExceed() throws LimitExceededException {
        Drone drone = new Drone(1L, "AUTOTEST_DRONE1", (float) 0.9, DroneState.IDLE, DroneModel.Heavyweight);
        LoadingDroneRequestBody requestBody = new LoadingDroneRequestBody(drone.getId(), List.of(1L, 2L));

        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication(1L, "AutoTestMedication1", 250, "TEST-1", new byte[]{0x02, 0x00, 0x00, 0x00, 0x45, 0x00, 0x05}, null));
        medications.add(new Medication(2L, "AutoTestMedication2", 251, "TEST-2", new byte[]{0x02, 0x05, 0x05, 0x00, 0x45, 0x00, 0x00}, null));

        Mockito.when(droneRepository.findById(requestBody.getDroneId())).thenReturn(Optional.of(drone));
        Mockito.when(medicationRepository.findMedicationsByIds(requestBody.getMedicationIds())).thenReturn(medications);
        Mockito.when(droneRepository.getReferenceById(drone.getId())).thenReturn(drone);

        List<Medication> oldMedications = new ArrayList<>();
        Mockito.when(medicationRepository.findMedicationsByDroneId(drone.getId())).thenReturn(oldMedications);
        medicationService.loadingDroneWithMedications(requestBody);
    }

    //loading a drone with medication items test5
    @Test(expected = IllegalStateException.class)
    public void should_ReturnIllegalStateException_IfMedicationIsAlreadyTaken() throws LimitExceededException {
        Drone drone = new Drone(1L, "AUTOTEST_DRONE1", (float) 0.9, DroneState.IDLE, DroneModel.Heavyweight);
        Drone anotherDrone = new Drone(2L, "AUTOTEST_DRONE2", (float) 0.9, DroneState.IDLE, DroneModel.Heavyweight);
        LoadingDroneRequestBody requestBody = new LoadingDroneRequestBody(drone.getId(), List.of(1L, 2L));

        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication(1L, "AutoTestMedication1", 100, "TEST-1", new byte[]{0x02, 0x00, 0x00, 0x00, 0x45, 0x00, 0x05}, null));
        medications.add(new Medication(2L, "AutoTestMedication2", 200, "TEST-2", new byte[]{0x02, 0x05, 0x05, 0x00, 0x45, 0x00, 0x00}, anotherDrone));

        List<MedicationResponseBody> expectedResponse = new ArrayList<>();
        expectedResponse.add(MedicationResponseBody.getBuilder()
                .setId(1L)
                .setName("AutoTestMedication1")
                .setWeight(100)
                .setCode("TEST-1")
                .setImage(new byte[]{0x02, 0x00, 0x00, 0x00, 0x45, 0x00, 0x05})
                .setDroneId(drone.getId())
                        .build());
        expectedResponse.add(MedicationResponseBody.getBuilder()
                .setId(2L)
                .setName("AutoTestMedication2")
                .setWeight(200)
                .setCode("TEST-2")
                .setImage(new byte[]{0x02, 0x05, 0x05, 0x00, 0x45, 0x00, 0x00})
                .setDroneId(drone.getId())
                        .build());

        Mockito.when(droneRepository.findById(requestBody.getDroneId())).thenReturn(Optional.of(drone));
        Mockito.when(medicationRepository.findMedicationsByIds(requestBody.getMedicationIds())).thenReturn(medications);
        Mockito.when(droneRepository.getReferenceById(drone.getId())).thenReturn(drone);

        List<Medication> oldMedications = new ArrayList<>();
        Mockito.when(medicationRepository.saveAndFlush(medications.get(0))).thenReturn(medications.get(0));
        Mockito.when(medicationRepository.findMedicationsByDroneId(drone.getId())).thenReturn(oldMedications);

        medicationService.loadingDroneWithMedications(requestBody);
    }

    //checking loaded medication items for a given drone test1
    @Test
    public void should_ReturnLoadedMedicationsForDrone_IfDroneExist() {
        Drone drone = new Drone(1L, "AUTOTEST_DRONE1", (float) 0.9, DroneState.IDLE, DroneModel.Heavyweight);

        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication(1L, "AutoTestMedication1", 100, "TEST-1", new byte[]{0x02, 0x00, 0x00, 0x00, 0x45, 0x00, 0x05}, drone));
        medications.add(new Medication(2L, "AutoTestMedication2", 200, "TEST-2", new byte[]{0x02, 0x05, 0x05, 0x00, 0x45, 0x00, 0x00}, drone));

        List<MedicationResponseBody> expectedResponse = new ArrayList<>();
        expectedResponse.add(MedicationResponseBody.getBuilder()
                .setId(1L)
                .setName("AutoTestMedication1")
                .setWeight(100)
                .setCode("TEST-1")
                .setImage(new byte[]{0x02, 0x00, 0x00, 0x00, 0x45, 0x00, 0x05})
                .setDroneId(drone.getId())
                        .build());
        expectedResponse.add(MedicationResponseBody.getBuilder()
                .setId(2L)
                .setName("AutoTestMedication2")
                .setWeight(200)
                .setCode("TEST-2")
                .setImage(new byte[]{0x02, 0x05, 0x05, 0x00, 0x45, 0x00, 0x00})
                .setDroneId(drone.getId())
                        .build());

        Mockito.when(droneRepository.findById(drone.getId())).thenReturn(Optional.of(drone));
        Mockito.when(medicationRepository.findMedicationsByDroneId(drone.getId())).thenReturn(medications);

        Assert.assertEquals(expectedResponse, medicationService.getLoadedMedicationsByDroneId(drone.getId()));
    }

    //checking loaded medication items for a given drone test2
    @Test(expected = IllegalArgumentException.class)
    public void should_ReturnLoadedMedicationsForDrone_IfDroneDoesNotExist() {
        Mockito.when(droneRepository.findById(1L)).thenReturn(Optional.empty());
        medicationService.getLoadedMedicationsByDroneId(1L);
    }

    //registering a list of medications
    @Test
    public void should_ReturnRegisteredMedications_IfMedicationsAreCorrect() {
        Drone drone = new Drone(1L, "AUTOTEST_DRONE1", (float) 0.9, DroneState.LOADING, DroneModel.Heavyweight);
        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication(1L, "AutoTestMedication1", 100, "TEST-1", new byte[]{0x02, 0x00, 0x00, 0x00, 0x45, 0x00, 0x05}, drone));
        medications.add(new Medication(2L, "AutoTestMedication2", 200, "TEST-2", new byte[]{0x02, 0x05, 0x05, 0x00, 0x45, 0x00, 0x00}, null));

        Mockito.when(medicationRepository.saveAll(medications)).thenReturn(medications);
        Assert.assertEquals(medications, medicationService.saveAll(medications));
    }
}
