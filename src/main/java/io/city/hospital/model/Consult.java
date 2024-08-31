package io.city.hospital.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "consults")
public class Consult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(mappedBy = "consult", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Symptom> symptoms = new ArrayList<>();
}

