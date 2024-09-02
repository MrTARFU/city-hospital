package io.city.hospital.web.controller;

import io.city.hospital.model.dto.ConsultDto;
import io.city.hospital.model.dto.PatientConsultResponse;
import io.city.hospital.model.dto.PatientDto;
import io.city.hospital.model.dto.SymptomDto;
import io.city.hospital.service.PatientService;
import io.city.hospital.web.error.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.city.hospital.AppConstants.X_ENVIRONMENT;
import static io.city.hospital.TestUtil.TEST_ENVIRONMENT;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatientControllerTest {

    private MockMvc restPromiseMockMvc;

    @MockBean
    private PatientService patientService;

    private List<PatientDto> mockPatientDtos;

    private PatientConsultResponse mockPatientConsultResponse;

    @BeforeEach
    public void setUpForEachTest(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.restPromiseMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint())
                )
                .build();
    }

    @BeforeAll
    public void setUp() {
        // Initialize mock data
        mockPatientDtos = createMockPatients();
        mockPatientConsultResponse = createMockPatientConsultResponse();
    }

    @Test
    void getPatientConsultsAndSymptoms_Success() throws Exception {
        Long patientId = 1L;

        when(patientService.getPatientConsultsAndSymptoms(patientId)).thenReturn(mockPatientConsultResponse);

        restPromiseMockMvc.perform(get("/api/patients/consults/{id}", patientId)
                        .header(X_ENVIRONMENT, TEST_ENVIRONMENT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Consults[0].ConsultId").value(mockPatientConsultResponse.getConsults().getFirst().getConsultId()))
                .andExpect(jsonPath("$.Consults[0].Doctor").value(mockPatientConsultResponse.getConsults().getFirst().getDoctor()))
                .andExpect(jsonPath("$.Consults[0].Specialty").value(mockPatientConsultResponse.getConsults().getFirst().getSpecialty()))
                .andExpect(jsonPath("$.Symptoms[0].SymptomId").value(mockPatientConsultResponse.getSymptoms().getFirst().getId()))
                .andExpect(jsonPath("$.Symptoms[0].Description").value(mockPatientConsultResponse.getSymptoms().getFirst().getDescription()))
                .andReturn();
    }

    @Test
    void getPatientConsultsAndSymptoms_PatientNotFound() throws Exception {
        Long patientId = 1L;

        when(patientService.getPatientConsultsAndSymptoms(patientId))
                .thenThrow(new ResourceNotFoundException("Patient with number: " + patientId + " not found."));

        restPromiseMockMvc.perform(get("/api/patients/consults/{id}", patientId)
                        .header(X_ENVIRONMENT, TEST_ENVIRONMENT)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient with number: " + patientId + " not found."));
    }

    @Test
    void getAllPatientsWithSuccess() throws Exception {
        Page<PatientDto> patientPage = new PageImpl<>(mockPatientDtos);

        when(patientService.getAllPatients(anyInt(), anyInt(), anyString())).thenReturn(patientPage);

        restPromiseMockMvc.perform(get("/api/patients")
                        .header(X_ENVIRONMENT, TEST_ENVIRONMENT)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].Id").value(mockPatientDtos.getFirst().getId()))
                .andExpect(jsonPath("$.content[0].Name").value(mockPatientDtos.getFirst().getName()))
                .andExpect(jsonPath("$.content[0].Age").value(mockPatientDtos.getFirst().getAge()))
                .andReturn();
    }

    @Test
    void getAllPatientsWithEmptyResult() throws Exception {
        Page<PatientDto> emptyPage = new PageImpl<>(Collections.emptyList());

        when(patientService.getAllPatients(anyInt(), anyInt(), anyString())).thenReturn(emptyPage);

        restPromiseMockMvc.perform(get("/api/patients")
                        .header(X_ENVIRONMENT, TEST_ENVIRONMENT)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    void getAllPatientsWithInvalidParameter() throws Exception {
        when(patientService.getAllPatients(anyInt(), anyInt(), eq("invalidSort"))).thenThrow(new IllegalArgumentException("Invalid sort parameter"));

        restPromiseMockMvc.perform(get("/api/patients")
                        .header(X_ENVIRONMENT, TEST_ENVIRONMENT)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "invalidSort")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid sort parameter"));
    }

    private PatientConsultResponse createMockPatientConsultResponse() {
        // Create mock PatientConsultResponse data
        List<ConsultDto> consults = new ArrayList<>();
        consults.add(new ConsultDto(1L, "Dr. Smith", "Cardiology"));
        consults.add(new ConsultDto(2L, "Dr. Adams", "Dermatology"));

        List<SymptomDto> symptoms = new ArrayList<>();
        symptoms.add(new SymptomDto(1L, "Headache"));
        symptoms.add(new SymptomDto(2L, "Fever"));

        return new PatientConsultResponse(consults, symptoms);
    }

    private List<PatientDto> createMockPatients() {
        PatientDto patient1 = new PatientDto(1L,"Miguel",40,List.of(new ConsultDto(1L, "António", "Dermatology")));
        PatientDto patient2 = new PatientDto(2L,"Ana",30,List.of(new ConsultDto(2L, "Maria", "Cardiology")));
        return List.of(patient1, patient2);
    }
}
