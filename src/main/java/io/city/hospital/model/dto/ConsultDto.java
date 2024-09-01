package io.city.hospital.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsultDto {
    @JsonProperty("ConsultId")
    private Long consultId;

    @JsonProperty("Doctor")
    private String doctor;

    @JsonProperty("Specialty")
    private String specialty;

    public ConsultDto(Long consultId, String doctor, String specialty) {
        this.doctor = doctor;
        this.consultId = consultId;
        this.specialty = specialty;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setConsultId(Long consultId) {
        this.consultId = consultId;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Long getConsultId() {
        return consultId;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getDoctor() {
        return doctor;
    }
}
