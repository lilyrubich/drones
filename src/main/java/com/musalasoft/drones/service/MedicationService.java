package com.musalasoft.drones.service;

import com.musalasoft.drones.body.LoadingDroneRequestBody;
import com.musalasoft.drones.body.MedicationResponseBody;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.enums.DroneState;
import com.musalasoft.drones.repository.DroneRepository;
import com.musalasoft.drones.repository.MedicationRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.naming.LimitExceededException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {

    private final Logger logger = LoggerFactory.getLogger(MedicationService.class);
    private final MedicationRepository medicationRepository;
    private final DroneRepository droneRepository;

    public MedicationService(MedicationRepository medicationRepository, DroneRepository droneRepository) {
        this.medicationRepository = medicationRepository;
        this.droneRepository = droneRepository;
    }

    //loading a drone with medication items
    @Transactional
    public List<MedicationResponseBody> loadingDroneWithMedications(LoadingDroneRequestBody loadingDroneRequestBody) throws LimitExceededException, IllegalStateException, IllegalArgumentException {

        List<Medication> medications;
        medications = medicationRepository.findMedicationsByIds(loadingDroneRequestBody.getMedicationIds());
        Drone drone = droneRepository.getReferenceById(loadingDroneRequestBody.getDroneId());

        //check that drone is available for loading
        if ((drone.getState() != DroneState.IDLE && drone.getState() != DroneState.LOADING) || drone.getBatteryCapacity() < 0.25)
            throw new IllegalStateException("The drone cannot be loaded at the moment. Drone is already busy or not charged enough");

        //medications that already loaded to the drone
        List<Medication> oldMedicationsById = medicationRepository.findMedicationsByDroneId(drone.getId());
        int weightMedicationsSum = 0;
        int droneWeightLimit = drone.getModel().getLimit();

        if (!oldMedicationsById.isEmpty()) {
            for (Medication medication : oldMedicationsById) {
                weightMedicationsSum += medication.getWeight();
            }
        }
        for (Medication medication : medications) {
            weightMedicationsSum += medication.getWeight();
            if (weightMedicationsSum > droneWeightLimit) {
                throw new LimitExceededException("Drone load limit exceeded");
            }
        }


        //loading the drone
        for (Medication medication : medications) {
            if (medication.getDrone() == null) {
                medication.setDrone(drone);
                medication.getDrone().setState(DroneState.LOADING);
                medicationRepository.saveAndFlush(medication);
            } else
                throw new IllegalStateException("The medication is already taken by the drone");
        }
        logger.info("Drone with id = " + drone.getId() + " has loaded with medications: " + medications);

        //merge old and new medications for the drone
        medications.addAll(oldMedicationsById);

        return this.toResponseBody(medications);
    }

    //checking loaded medication items for a given drone
    public List<MedicationResponseBody> getLoadedMedicationsByDroneId(Long droneId) {
        Optional<Drone> drone = droneRepository.findById(droneId);
        if (drone.isEmpty())
            throw new IllegalArgumentException("Drone with droneId = " + droneId + " doesn't exist");

        List<Medication> medications = medicationRepository.findMedicationsByDroneId(droneId);

        return this.toResponseBody(medications);
    }

    public List<Medication> saveAll(List<Medication> medications) {
        List<Medication> response = medicationRepository.saveAll(medications);
        logger.info("New medications have registered: " + medications);
        return response;
    }

    //parse result for HTTP Response
    public List<MedicationResponseBody> toResponseBody(List<Medication> medications) {
        List<MedicationResponseBody> response = new ArrayList<>();
        for (Medication medication : medications) {
            MedicationResponseBody medicationResponseBody = MedicationResponseBody.getBuilder()
                    .setId(medication.getId())
                    .setName(medication.getName())
                    .setWeight(medication.getWeight())
                    .setCode(medication.getCode())
                    .setImage(medication.getImage())
                    .setDroneId(medication.getDrone().getId())
                    .build();
            response.add(medicationResponseBody);
        }
        return response;
    }
}
