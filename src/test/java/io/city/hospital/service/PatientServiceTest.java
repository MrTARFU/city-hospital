package io.city.hospital.service;

import io.city.hospital.model.Consult;
import io.city.hospital.model.Patient;
import io.city.hospital.model.Specialty;
import io.city.hospital.model.Symptom;
import io.city.hospital.model.dto.PatientConsultResponse;
import io.city.hospital.model.dto.PatientDto;
import io.city.hospital.repository.PatientRepository;
import io.city.hospital.web.error.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @MockBean
    private PatientRepository patientRepository;

    private Patient mockPatient;
    private List<Patient> mockPatients;

    @BeforeAll
    public void setUp() {
        // Initialize mock data
        mockPatient = createMockPatient();
        mockPatients = createMockPatients();
    }

    @Test
    void getPatientConsultsAndSymptoms_Success() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(mockPatient));

        PatientConsultResponse result = patientService.getPatientConsultsAndSymptoms(1L);

        assertNotNull(result);
        assertEquals(2, result.getConsults().size());
        assertEquals(3, result.getSymptoms().size());
        assertEquals("António", result.getConsults().getFirst().getDoctor());
        assertEquals("Dermatology", result.getConsults().getFirst().getSpecialty());
        assertEquals("Symptom 1 Description", result.getSymptoms().getFirst().getDescription());

        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void getPatientConsultsAndSymptoms_PatientNotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class,
                () -> patientService.getPatientConsultsAndSymptoms(1L));

        String expectedMessage = "Patient with number: 1 not found.";
        assertEquals(expectedMessage, exception.getMessage());

        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void getAllPatients_Success() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
        Page<Patient> patientPage = new PageImpl<>(mockPatients, pageable, mockPatients.size());

        when(patientRepository.findAll(pageable)).thenReturn(patientPage);

        Page<PatientDto> result = patientService.getAllPatients(0, 10, "name");

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Miguel", result.getContent().getFirst().getName());
        assertEquals(40, result.getContent().getFirst().getAge());

        verify(patientRepository, times(1)).findAll(pageable);
    }

    @Test
    void getAllPatients_EmptyResult() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<Patient> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(patientRepository.findAll(pageable)).thenReturn(emptyPage);

        Page<PatientDto> result = patientService.getAllPatients(0, 10, "id");

        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());

        verify(patientRepository, times(1)).findAll(pageable);
    }

    @Test
    void getAllPatients_InvalidSortParameter() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("invalidField"));

        when(patientRepository.findAll(pageable)).thenThrow(new IllegalArgumentException("Invalid sort parameter"));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> patientService.getAllPatients(0, 10, "invalidField"));

        String expectedMessage = "Invalid sort parameter";
        assertEquals(expectedMessage, exception.getMessage());

        verify(patientRepository, times(1)).findAll(pageable);
    }

    private Patient createMockPatient() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Miguel");
        patient.setAge(40);

        List<Consult> consults = new ArrayList<>();

        Consult consult1 = getConsult();
        consults.add(consult1);

        Consult consult2 = getConsult2();
        consults.add(consult2);

        patient.setConsults(consults);

        return patient;
    }

    private static Consult getConsult() {
        Consult consult1 = new Consult();
        consult1.setId(1L);
        consult1.setDoctor("António");
        Specialty special1 = new Specialty();
        special1.setId(1L);
        special1.setName("Dermatology");
        consult1.setSpecialty(special1);

        Symptom symptom1 = new Symptom();
        symptom1.setId(1L);
        symptom1.setDescription("Symptom 1 Description");
        Symptom symptom2 = new Symptom();
        symptom2.setId(2L);
        symptom2.setDescription("Symptom 2 Description");

        consult1.setSymptoms(List.of(symptom1, symptom2));
        return consult1;
    }

    private static Consult getConsult2() {
        Consult consult2 = new Consult();
        consult2.setId(2L);
        consult2.setDoctor("Maria");
        Specialty special2 = new Specialty();
        special2.setId(2L);
        special2.setName("Dermatology");
        consult2.setSpecialty(special2);

        Symptom symptom3 = new Symptom();
        symptom3.setId(3L);
        symptom3.setDescription("Symptom 3 Description");

        consult2.setSymptoms(List.of(symptom3));
        return consult2;
    }

    private List<Patient> createMockPatients() {
        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setName("Miguel");
        patient1.setAge(40);

        Consult consult1 = new Consult();
        consult1.setId(1L);
        consult1.setDoctor("António");
        Specialty specialty1 = new Specialty();
        specialty1.setId(1L);
        specialty1.setName("Dermatology");
        consult1.setSpecialty(specialty1);

        patient1.setConsults(List.of(consult1));

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setName("Ana");
        patient2.setAge(30);

        Consult consult2 = new Consult();
        consult2.setId(2L);
        consult2.setDoctor("Maria");
        Specialty specialty2 = new Specialty();
        specialty2.setId(2L);
        specialty2.setName("Dermatology");
        consult2.setSpecialty(specialty2);

        patient2.setConsults(List.of(consult2));

        return List.of(patient1, patient2);
    }
}

