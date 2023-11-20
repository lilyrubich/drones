package com.musalasoft.drones.enums;

public enum DroneState {
    IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING;


    public String toString(DroneState state) {
        return state.name();
    }
}
