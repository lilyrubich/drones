package com.musalasoft.drones.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "check_battery_task_log")
public class CheckBatteryTaskLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "droneId", nullable = false)
    private Long droneId;

    @Column(name = "battery_capacity", columnDefinition = "DECFLOAT(2)", nullable = false)
    private Float batteryCapacity;

    public CheckBatteryTaskLog() {
    }

    public CheckBatteryTaskLog(LocalDateTime date, Long droneId, Float batteryCapacity) {
        this.date = date;
        this.droneId = droneId;
        this.batteryCapacity = batteryCapacity;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getDroneId() {
        return droneId;
    }

    public Float getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDroneId(Long droneId) {
        this.droneId = droneId;
    }

    public void setBatteryCapacity(Float batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }
}
