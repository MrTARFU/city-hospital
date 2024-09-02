package io.city.hospital.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsultInsertDto {
    @JsonProperty("Doctor")
    private String doctor;
    @JsonProperty("PatientId")
    private Long patientId;
    @JsonProperty("SpecialtyId")
    private Long specialtyId;

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Long specialtyId) {
        this.specialtyId = specialtyId;
    }
}
