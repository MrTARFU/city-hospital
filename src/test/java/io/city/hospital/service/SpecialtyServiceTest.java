package io.city.hospital.service;

import io.city.hospital.model.dto.TopSpecialtyResponse;
import io.city.hospital.repository.SpecialtyRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpecialtyServiceTest {

    @Autowired
    private SpecialtyService specialtyService;

    @MockBean
    private SpecialtyRepository specialtyRepository;

    private List<TopSpecialtyResponse> mockTopSpecialtiesCase10;
    private List<TopSpecialtyResponse> mockTopSpecialtiesCase16;

    @BeforeAll
    public void setUp() {
        // Initialize mock data
        mockTopSpecialtiesCase10 = List.of(
                new TopSpecialtyResponse("Dermatology", 20L),
                new TopSpecialtyResponse("Cardiology", 15L)
        );

        mockTopSpecialtiesCase16 = List.of(
                new TopSpecialtyResponse("Dermatology", 20L)
        );
    }

    @Test
    void getTopSpecialtiesWithMin10Patients_Success() {
        when(specialtyRepository.findSpecialtiesWithMinPatients(10)).thenReturn(mockTopSpecialtiesCase10);

        List<TopSpecialtyResponse> result = specialtyService.getTopSpecialties(10);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Dermatology", result.getFirst().getSpecialtyName());
        assertEquals(20L, result.getFirst().getNumberOfPatients());
        assertEquals("Cardiology", result.get(1).getSpecialtyName());
        assertEquals(15L, result.get(1).getNumberOfPatients());

        verify(specialtyRepository, times(1)).findSpecialtiesWithMinPatients(10);
    }

    @Test
    void getTopSpecialtiesWithMin16Patients_Success() {
        when(specialtyRepository.findSpecialtiesWithMinPatients(16)).thenReturn(mockTopSpecialtiesCase16);

        List<TopSpecialtyResponse> result = specialtyService.getTopSpecialties(16);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Dermatology", result.getFirst().getSpecialtyName());
        assertEquals(20L, result.getFirst().getNumberOfPatients());

        verify(specialtyRepository, times(1)).findSpecialtiesWithMinPatients(16);
    }

    @Test
    void getTopSpecialtiesWithMinPatients_EmptyResult() {
        when(specialtyRepository.findSpecialtiesWithMinPatients(100)).thenReturn(Collections.emptyList());

        List<TopSpecialtyResponse> result = specialtyService.getTopSpecialties(100);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(specialtyRepository, times(1)).findSpecialtiesWithMinPatients(100);
    }
}

