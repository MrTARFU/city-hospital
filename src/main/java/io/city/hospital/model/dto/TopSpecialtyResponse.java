package io.city.hospital.model.dto;

public class TopSpecialtyResponse {
    private String specialtyName;
    private Long numberOfPatients;

    public TopSpecialtyResponse(String specialtyName, Long numberOfPatients) {
        this.specialtyName = specialtyName;
        this.numberOfPatients = numberOfPatients;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public Long getNumberOfPatients() {
        return numberOfPatients;
    }

    public void setNumberOfPatients(Long numberOfPatients) {
        this.numberOfPatients = numberOfPatients;
    }
}

