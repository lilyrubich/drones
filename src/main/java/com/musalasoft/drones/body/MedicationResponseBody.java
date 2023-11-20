package com.musalasoft.drones.body;

import java.util.Arrays;
import java.util.Objects;

public class MedicationResponseBody {

    private final Long id;
    private final String name;
    private final Integer weight;
    private final String code;
    private final byte[] image;
    private final Long droneId;

    public MedicationResponseBody(MedicationResponseBody.Builder builder) {
        this.id = builder.getId();
        this.name = builder.getName();
        this.weight = builder.getWeight();
        this.code = builder.getCode();
        this.image = builder.getImage();
        this.droneId = builder.getDroneId();
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

    public Long getDroneId() {
        return droneId;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicationResponseBody that = (MedicationResponseBody) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(weight, that.weight) && Objects.equals(code, that.code) && Arrays.equals(image, that.image) && Objects.equals(droneId, that.droneId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, weight, code, droneId);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    public static class Builder {
        private Long id;
        private String name;
        private Integer weight;
        private String code;
        private byte[] image;
        private Long droneId;

        public Long getId() {
            return id;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Integer getWeight() {
            return weight;
        }

        public Builder setWeight(Integer weight) {
            this.weight = weight;
            return this;
        }

        public String getCode() {
            return code;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public byte[] getImage() {
            return image;
        }

        public Builder setImage(byte[] image) {
            this.image = image;
            return this;
        }

        public Long getDroneId() {
            return droneId;
        }

        public Builder setDroneId(Long droneId) {
            this.droneId = droneId;
            return this;
        }

        public MedicationResponseBody build() {
            return new MedicationResponseBody(this);
        }
    }
}
