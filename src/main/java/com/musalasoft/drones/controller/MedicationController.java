package com.musalasoft.drones.controller;

import com.musalasoft.drones.body.LoadingDroneRequestBody;
import com.musalasoft.drones.body.MedicationResponseBody;
import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.service.MedicationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.LimitExceededException;
import java.util.List;

@RestController
public class MedicationController {

    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping("/loadingDroneWithMedications")
    public List<MedicationResponseBody> loadingDroneWithMedications(@RequestBody LoadingDroneRequestBody loadingDroneRequestBody) {
        try {
            return medicationService.loadingDroneWithMedications(loadingDroneRequestBody);
        } catch (LimitExceededException | IllegalStateException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/medications")
    public List<MedicationResponseBody> getMedications(@RequestParam Long droneId) {
        try {
            return medicationService.getLoadedMedicationsByDroneId(droneId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
