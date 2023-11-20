package com.musalasoft.drones.repository;

import com.musalasoft.drones.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    @Query(nativeQuery = true, value = "select * from medication where medication_id in (:medicationIds)")
    List<Medication> findMedicationsByIds(@Param("medicationIds") List<Long> medicationIds);

    @Query(nativeQuery = true, value = "select * from medication where drone_id=:droneId")
    List<Medication> findMedicationsByDroneId(@Param("droneId") Long droneId);
}
