package com.musalasoft.drones.body;

import java.util.List;

public class LoadingDroneRequestBody {

    private Long DroneId;

    private List<Long> MedicationIds;

    public LoadingDroneRequestBody(Long droneId, List<Long> medicationIds) {
        DroneId = droneId;
        MedicationIds = medicationIds;
    }

    public Long getDroneId() {
        return DroneId;
    }

    public void setDroneId(Long droneId) {
        DroneId = droneId;
    }

    public List<Long> getMedicationIds() {
        return MedicationIds;
    }

    public void setMedicationsId(List<Long> medicationIds) {
        MedicationIds = medicationIds;
    }
}
