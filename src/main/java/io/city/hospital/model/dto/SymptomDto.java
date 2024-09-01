package io.city.hospital.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SymptomDto {
    @JsonProperty("SymptomId")
    private Long id;

    @JsonProperty("Description")
    private String description;

    public SymptomDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
