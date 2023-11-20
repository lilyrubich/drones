package com.musalasoft.drones.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.musalasoft.drones.enums.DroneModel;
import com.musalasoft.drones.enums.DroneState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

import java.util.Objects;

/*A Drone has:

        serial number (100 characters max);
        model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
        weight limit (500gr max);
        battery capacity (percentage);
        state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).*/

@Entity
@Table(name = "drone")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "drone_id", nullable = false)
    private Long id;

    @Column(name = "serial_number", columnDefinition = "VARCHAR(100)", nullable = false)
    @NotBlank(message = "Serial number must be specified")
    @Size(max = 100, message = "Serial number should not be greater than 100")
    private String serialNumber;

    @Column(name = "battery_capacity", columnDefinition = "DECFLOAT(2)", nullable = false)
    @NotNull(message = "Battery capacity must be between 0 and 1")
    @Range(min = 0, max = 1)
    private Float batteryCapacity;

    @Column(name = "state", columnDefinition = "VARCHAR(20)", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "State must be specified")
    private DroneState state;

    @Column(name = "model", columnDefinition = "VARCHAR(30)", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Model must be specified")
    private DroneModel model;

    public Drone() {
    }

    public Drone(Long id, String serialNumber, Float batteryCapacity, DroneState state, DroneModel model) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
        this.model = model;
    }

    @Override
    public String toString() {
        return "DroneEntity{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", batterCapacity=" + batteryCapacity + '\'' +
                ", state=" + state + '\'' +
                ", model=" + model +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setBatteryCapacity(Float batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public void setState(DroneState state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Float getBatteryCapacity() {
        return batteryCapacity;
    }

    public DroneState getState() {
        return state;
    }

    public DroneModel getModel() {
        return model;
    }

    public void setModel(DroneModel droneModel) {
        this.model = droneModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drone drone = (Drone) o;
        return Objects.equals(id, drone.id) && Objects.equals(serialNumber, drone.serialNumber) && Objects.equals(batteryCapacity, drone.batteryCapacity) && state == drone.state && model == drone.model;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, batteryCapacity, state, model);
    }
}
