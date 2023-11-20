package com.musalasoft.drones.controller;

import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.service.DroneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping("/registerDrone")
    public Drone registerDrone(@Valid @RequestBody Drone drone) {
        return droneService.save(drone);
    }

    @GetMapping("/checkAvailableDrones")
    public List<Drone> getAvailableDrones() {
        return droneService.getAvailableDronesForLoading();
    }

    @GetMapping("/checkDroneBatteryLevel")
    public Float getDroneBatteryLevel(@RequestParam Long droneId) {
        try {
            return droneService.checkDroneBatteryLevel(droneId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
