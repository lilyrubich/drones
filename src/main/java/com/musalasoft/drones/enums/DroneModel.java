package com.musalasoft.drones.enums;

public enum DroneModel {
    Lightweight(200),
    Middleweight(300),
    Cruiserweight(400),
    Heavyweight(500);

    private final int weightLimit;

    DroneModel(int weightLimit) {
        this.weightLimit = weightLimit;
    }

    public int getLimit() {
        return weightLimit;
    }

}
