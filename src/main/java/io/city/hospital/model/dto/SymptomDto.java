package io.city.hospital.model.dto;

public class SymptomDto {

    private Long id;
    private String description;

    public SymptomDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getSymptomId() {
        return id;
    }

    public void setSymptomId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
