package io.city.hospital.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateConsultResponseDto {
    @JsonProperty("ConsultId")
    private long id;
    @JsonProperty("Doctor")
    private String doctor;
    @JsonProperty("PatientName")
    private String patientName;
    @JsonProperty("SpecialtyName")
    private String specialtyName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }
}
