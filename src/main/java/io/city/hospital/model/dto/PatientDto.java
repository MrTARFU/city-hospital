package io.city.hospital.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PatientDto {
    @JsonProperty("Id")
    private Long id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Age")
    private Integer age;
    @JsonProperty("Consults")
    private List<ConsultDto> consults;

    public PatientDto(Long id, String name, Integer age, List<ConsultDto> consults) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.consults = consults;
    }

    public List<ConsultDto> getConsults() {
        return consults;
    }

    public void setConsults(List<ConsultDto> consults) {
        this.consults = consults;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
