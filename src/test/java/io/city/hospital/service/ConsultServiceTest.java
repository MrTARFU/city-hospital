package io.city.hospital.service;

import io.city.hospital.TestUtil;
import io.city.hospital.model.Consult;
import io.city.hospital.model.Patient;
import io.city.hospital.model.Specialty;
import io.city.hospital.model.Symptom;
import io.city.hospital.model.dto.ConsultInsertDto;
import io.city.hospital.model.dto.CreateConsultResponseDto;
import io.city.hospital.repository.ConsultRepository;
import io.city.hospital.repository.PatientRepository;
import io.city.hospital.repository.SpecialtyRepository;
import io.city.hospital.web.error.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConsultServiceTest {

    private MockMvc restPromiseMockMvc;

    @Autowired
    private ConsultService consultService;

    private ConsultRepository consultRepository;
    @MockBean
    private PatientRepository patientRepository;
    @MockBean
    private SpecialtyRepository specialtyRepository;

    private ConsultInsertDto mockConsultInsertDto;

    @BeforeAll
    public void setUp() throws IOException {

        try {
            var jsonConsultInsertDto = new File("src/test/resources/ConsultInsertDto.json");

            mockConsultInsertDto = TestUtil.convertBytesJsonToObject(jsonConsultInsertDto, ConsultInsertDto.class);
        } catch (IOException e){
            throw new IOException("Error access json test file");
        }
    }

    @Test
    void createConsultWithSuccess() {
        when(patientRepository.findById(mockConsultInsertDto.getPatientId())).thenReturn(Optional.ofNullable(returnPatient()));
        when(specialtyRepository.findById(mockConsultInsertDto.getSpecialtyId())).thenReturn(Optional.ofNullable(returnSpecialty()));


        Consult consult = new Consult();
        consult.setDoctor(mockConsultInsertDto.getDoctor());
        consult.setSpecialty(returnSpecialty());
        consult.setPatient(returnPatient());

        consult.setId(11L);

        CreateConsultResponseDto createConsultResponseDto = new CreateConsultResponseDto();
        createConsultResponseDto.setId(consult.getId());
        createConsultResponseDto.setDoctor(consult.getDoctor());
        createConsultResponseDto.setPatientName(consult.getPatient().getName());
        createConsultResponseDto.setSpecialtyName(consult.getSpecialty().getName());

        var result = consultService.createConsult(mockConsultInsertDto);
        createConsultResponseDto.setId(result.getId());
        assertEquals(createConsultResponseDto, result);
    }

    @Test
    void createConsultWithPatientNotFound() {
        // Mock patient repository to return empty
        when(patientRepository.findById(mockConsultInsertDto.getPatientId())).thenReturn(Optional.empty());
        when(specialtyRepository.findById(mockConsultInsertDto.getSpecialtyId())).thenReturn(Optional.ofNullable(returnSpecialty()));

        // Expect ResourceNotFoundException for missing patient
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            consultService.createConsult(mockConsultInsertDto);
        });

        String expectedMessage = "Patient with number: " + mockConsultInsertDto.getPatientId() + " not found.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void createConsultWithSpecialtyNotFound() {
        // Mock specialty repository to return empty
        when(patientRepository.findById(mockConsultInsertDto.getPatientId())).thenReturn(Optional.ofNullable(returnPatient()));
        when(specialtyRepository.findById(mockConsultInsertDto.getSpecialtyId())).thenReturn(Optional.empty());

        // Expect ResourceNotFoundException for missing specialty
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            consultService.createConsult(mockConsultInsertDto);
        });

        String expectedMessage = "Specialty with id: " + mockConsultInsertDto.getSpecialtyId() + " not found.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    public Specialty returnSpecialty(){
        Specialty specialty = new Specialty();
        specialty.setId(1L);
        specialty.setName("Dermatology");

        List<Consult> consults = new ArrayList<>();
        Consult consult = new Consult();
        consult.setId(1L);
        consult.setDoctor("António");
        consult.setPatient(new Patient());

        consult.setSpecialty(new Specialty());
        Symptom symptom = new Symptom();
        symptom.setId(18L);
        symptom.setDescription("Symptom 14 Description");
        symptom.setConsult(consult);

        List<Symptom> symptoms = new ArrayList<>(1);
        symptoms.add(symptom);

        consult.setSymptoms(symptoms);

        consults.add(consult);

        specialty.setConsults(consults);

        return specialty;
    }

    public Patient returnPatient(){
        Patient patient = new Patient();
        patient.setId(6L);
        patient.setName("Miguel");
        patient.setAge(40);

        List<String> pathologies = new ArrayList<>(1);
        pathologies.add("Pathology 7");

        patient.setPathologies(pathologies);

        List<Consult> consults = new ArrayList<>(1);
        Consult consult = new Consult();
        consult.setId(8L);
        consult.setDoctor("Maria");

        Specialty specialty = new Specialty();

        consult.setSpecialty(specialty);

        Symptom symptom = new Symptom();
        symptom.setId(18L);
        symptom.setDescription("Symptom 14 Description");
        symptom.setConsult(consult);

        List<Symptom> symptoms = new ArrayList<>(1);
        symptoms.add(symptom);

        consult.setSymptoms(symptoms);
        consults.add(consult);

        patient.setConsults(consults);

        return patient;
    }
}
