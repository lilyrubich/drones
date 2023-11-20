package com.musalasoft.drones.service;

import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.enums.DroneModel;
import com.musalasoft.drones.enums.DroneState;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitiateData implements CommandLineRunner {

    private final DroneService droneService;
    private final MedicationService medicationService;

    public InitiateData(DroneService droneService, MedicationService medicationService) {
        this.droneService = droneService;
        this.medicationService = medicationService;
    }

    @Override
    public void run(String... args) {

        //Initialization of drones

        List<Drone> drones = new ArrayList<>();

        Drone firstDrone = new Drone();
        firstDrone.setSerialNumber("933KKR6002222777700000");
        firstDrone.setBatteryCapacity((float) 0.2);
        firstDrone.setState(DroneState.DELIVERED);
        firstDrone.setModel(DroneModel.Lightweight);
        drones.add(firstDrone);

        Drone secondDrone = new Drone();
        secondDrone.setSerialNumber("TT6483901955P");
        secondDrone.setBatteryCapacity((float) 0.92);
        secondDrone.setState(DroneState.IDLE);
        secondDrone.setModel(DroneModel.Cruiserweight);
        drones.add(secondDrone);

        Drone thirdDrone = new Drone();
        thirdDrone.setSerialNumber("DE9388821990S");
        thirdDrone.setBatteryCapacity((float) 0.55);
        thirdDrone.setState(DroneState.LOADED);
        thirdDrone.setModel(DroneModel.Heavyweight);
        drones.add(thirdDrone);

        droneService.saveAll(drones);


        //Initialization of medications
        List<Medication> medications = new ArrayList<>();

        Medication firstMedication = new Medication();
        firstMedication.setName("Nurofen-Forte_50gr");
        firstMedication.setWeight(60);
        firstMedication.setCode("DEOS_666");
        firstMedication.setImage(new byte[]{0x04, 0x05, 0x45, 0x00, 0x00, 0x02, 0x02});
        firstMedication.setDrone(thirdDrone);
        medications.add(firstMedication);

        Medication secondMedication = new Medication();
        secondMedication.setName("Adrenalin420");
        secondMedication.setWeight(120);
        secondMedication.setCode("420A");
        secondMedication.setImage(new byte[]{0x45, 0x05, 0x05, 0x02, 0x01, 0x00, 0x05});
        secondMedication.setDrone(thirdDrone);
        medications.add(secondMedication);

        Medication thirdMedication = new Medication();
        thirdMedication.setName("Insulin_injection_rapid-acting");
        thirdMedication.setWeight(350);
        thirdMedication.setCode("INSA2R");
        thirdMedication.setImage(new byte[]{0x01, 0x01, 0x02, 0x00, 0x03, 0x00, 0x45});
        medications.add(thirdMedication);

        Medication fourthMedication = new Medication();
        fourthMedication.setName("Insulin_injection_rapid-acting4000");
        fourthMedication.setWeight(1200);
        fourthMedication.setCode("S8888");
        fourthMedication.setImage(new byte[]{0x02, 0x45, 0x45, 0x00, 0x00, 0x03, 0x01});
        medications.add(fourthMedication);

        Medication fifthMedication = new Medication();
        fifthMedication.setName("TEWAAA-1");
        fifthMedication.setWeight(151);
        fifthMedication.setCode("092N");
        fifthMedication.setImage(new byte[]{0x01, 0x02, 0x00, 0x00, 0x4, 0x45, 0x00});
        medications.add(fifthMedication);

        Medication sixthMedication = new Medication();
        sixthMedication.setName("Spectra-boom");
        sixthMedication.setWeight(150);
        sixthMedication.setCode("7WW");
        sixthMedication.setImage(new byte[]{0x02, 0x00, 0x00, 0x00, 0x45, 0x00, 0x05});
        medications.add(sixthMedication);

        medicationService.saveAll(medications);

    }
}
