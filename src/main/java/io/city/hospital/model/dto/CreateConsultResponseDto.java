package io.city.hospital.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateConsultResponseDto that = (CreateConsultResponseDto) o;
        return id == that.id && Objects.equals(doctor, that.doctor) && Objects.equals(patientName, that.patientName) && Objects.equals(specialtyName, that.specialtyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doctor, patientName, specialtyName);
    }
}
