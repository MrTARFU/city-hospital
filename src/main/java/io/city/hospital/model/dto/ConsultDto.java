package io.city.hospital.model.dto;

public class ConsultDto {
    private Long patientId;
    private String doctor;
    private String specialty;

    public ConsultDto(Long patientId, String doctor, String specialty) {
        this.doctor = doctor;
        this.patientId = patientId;
        this.specialty = specialty;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setSpecialtyId(String specialty) {
        this.specialty = specialty;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getSpecialtyId() {
        return specialty;
    }

    public String getDoctor() {
        return doctor;
    }
}
