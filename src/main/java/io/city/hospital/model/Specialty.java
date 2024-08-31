package io.city.hospital.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "specialties")
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "specialty", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consult> consults = new ArrayList<>();
}

