package io.city.hospital.web.controller;

import io.city.hospital.TestUtil;
import io.city.hospital.model.dto.ConsultInsertDto;
import io.city.hospital.model.dto.CreateConsultResponseDto;
import io.city.hospital.service.ConsultService;
import io.city.hospital.web.error.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;

import static io.city.hospital.AppConstants.X_ENVIRONMENT;
import static io.city.hospital.TestUtil.TEST_ENVIRONMENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConsultControllerTest {

    private MockMvc restPromiseMockMvc;

    @MockBean
    private ConsultService consultService;

    private ConsultInsertDto mockConsultInsertDto;

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
    public void setUp() throws IOException {

        try {
            var jsonConsultInsertDto = new File("src/test/resources/ConsultInsertDto.json");

            mockConsultInsertDto = TestUtil.convertBytesJsonToObject(jsonConsultInsertDto, ConsultInsertDto.class);
        } catch (IOException e){
            throw new IOException("Error access json test file");
        }
    }

    @Test
    void createConsultWithSuccess() throws Exception {
        CreateConsultResponseDto responseDto = new CreateConsultResponseDto();
        responseDto.setId(14L);
        responseDto.setDoctor(mockConsultInsertDto.getDoctor());
        responseDto.setPatientName("Miguel");
        responseDto.setSpecialtyName("Dermatology");

        when(consultService.createConsult(any(ConsultInsertDto.class))).thenReturn(responseDto);

        var result = restPromiseMockMvc.perform(post("/api/consults")
                        .header(X_ENVIRONMENT, TEST_ENVIRONMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(mockConsultInsertDto)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.ConsultId").value(responseDto.getId()))
               .andExpect(jsonPath("$.Doctor").value(responseDto.getDoctor()))
               .andExpect(jsonPath("$.PatientName").value(responseDto.getPatientName()))
               .andExpect(jsonPath("$.SpecialtyName").value(responseDto.getSpecialtyName()))
               .andReturn();
        System.out.println("Response: " + result.getResponse().getContentAsString());
       assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    void createConsultWithPatientNotFound() throws Exception {
        when(consultService.createConsult(any(ConsultInsertDto.class)))
                .thenThrow(new ResourceNotFoundException("Patient with number: " + mockConsultInsertDto.getPatientId() + " not found."));

        restPromiseMockMvc.perform(post("/api/consults")
                        .header(X_ENVIRONMENT, TEST_ENVIRONMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(mockConsultInsertDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient with number: " + mockConsultInsertDto.getPatientId() + " not found."));
    }

    @Test
    void createConsultWithSpecialtyNotFound() throws Exception {
        when(consultService.createConsult(any(ConsultInsertDto.class)))
                .thenThrow(new ResourceNotFoundException("Specialty with id: " + mockConsultInsertDto.getSpecialtyId() + " not found."));

        restPromiseMockMvc.perform(post("/api/consults")
                        .header(X_ENVIRONMENT, TEST_ENVIRONMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(mockConsultInsertDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Specialty with id: " + mockConsultInsertDto.getSpecialtyId() + " not found."));
    }
}
