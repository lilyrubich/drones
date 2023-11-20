package com.musalasoft.drones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Arrays;
import java.util.Objects;

/*Medication has:

        name (allowed only letters, numbers, ‘-‘, ‘_’);
        weight;
        code (allowed only upper case letters, underscore and numbers);
        image (picture of the medication case).*/

@Entity
@Table(name = "medication")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "medication_id", nullable = false)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(40)", nullable = false)
    @NotBlank(message = "Name must be specified")
    @Pattern(regexp = "[a-zA-Z0-9_-]*", message = "Name can only contain letters, numbers, '-', '_'")
    private String name;

    @Column(name = "weight", columnDefinition = "INTEGER", nullable = false)
    @NotNull(message = "Weight must be specified")
    private Integer weight;

    @Column(name = "code", columnDefinition = "VARCHAR(20)", nullable = false)
    @NotBlank(message = "Code must be specified")
    @Pattern(regexp = "[A-Z0-9_]*", message = "Code can only contain upper case letters, numbers, '_'")
    private String code;

    @Column(name = "image", columnDefinition = "BLOB(10K)")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;

    public Medication() {
    }

    public Medication(Long id, String name, Integer weight, String code, byte[] image, Drone drone) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
        this.drone = drone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getCode() {
        return code;
    }

    public byte[] getImage() {
        return image;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public Medication setDroneAndGetMedication(Drone drone) {
        this.drone = drone;
        return this;
    }

    @Override
    public String toString() {
        return "MedicationEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight + '\'' +
                ", code=" + code + '\'' +
                ", drone=" + drone +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medication that = (Medication) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(weight, that.weight) && Objects.equals(code, that.code) && Arrays.equals(image, that.image) && Objects.equals(drone, that.drone);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, weight, code, drone);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
