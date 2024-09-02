package io.city.hospital.web.controller;

import io.city.hospital.model.dto.TopSpecialtyResponse;
import io.city.hospital.service.SpecialtyService;
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

import java.util.List;

import static io.city.hospital.AppConstants.X_ENVIRONMENT;
import static io.city.hospital.TestUtil.TEST_ENVIRONMENT;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpecialtyControllerTest {

    private MockMvc restPromiseMockMvc;

    @MockBean
    private SpecialtyService specialtyService;

    private List<TopSpecialtyResponse> mockSpecialtyResponses;

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
        mockSpecialtyResponses = createMockSpecialties();
    }

    @Test
    void getTopSpecialtiesWithSuccess() throws Exception {
        when(specialtyService.getTopSpecialties(anyInt())).thenReturn(mockSpecialtyResponses);

        restPromiseMockMvc.perform(get("/api/specialties/top")
                        .header(X_ENVIRONMENT, TEST_ENVIRONMENT)
                        .param("minPatients", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].SpecialtyName").value(mockSpecialtyResponses.get(0).getSpecialtyName()))
                .andExpect(jsonPath("$[0].NumberOfPatients").value(mockSpecialtyResponses.get(0).getNumberOfPatients()))
                .andExpect(jsonPath("$[1].SpecialtyName").value(mockSpecialtyResponses.get(1).getSpecialtyName()))
                .andExpect(jsonPath("$[1].NumberOfPatients").value(mockSpecialtyResponses.get(1).getNumberOfPatients()))
                .andReturn();
    }

    @Test
    void getTopSpecialtiesWithInvalidParameter() throws Exception {
        when(specialtyService.getTopSpecialties(anyInt())).thenThrow(new IllegalArgumentException("Invalid parameter"));

        restPromiseMockMvc.perform(get("/api/specialties/top")
                        .header(X_ENVIRONMENT, TEST_ENVIRONMENT)
                        .param("minPatients", "-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid parameter"));
    }

    private List<TopSpecialtyResponse> createMockSpecialties() {
        TopSpecialtyResponse specialty1 = new TopSpecialtyResponse("Dermatology", 20L);
        TopSpecialtyResponse specialty2 = new TopSpecialtyResponse("Cardiology", 15L);
        return List.of(specialty1, specialty2);
    }
}
