package io.city.hospital.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @ElementCollection
    private List<String> pathologies;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consult> consults = new ArrayList<>();

    public List<Consult> getConsults() {
        return consults;
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

    public List<String> getPathologies() {
        return pathologies;
    }

    public void setPathologies(List<String> pathologies) {
        this.pathologies = pathologies;
    }

    public void setConsults(List<Consult> consults) {
        this.consults = consults;
    }
}

