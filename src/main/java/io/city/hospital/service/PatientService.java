package io.city.hospital.service;

import io.city.hospital.model.Patient;
import io.city.hospital.model.dto.ConsultDto;
import io.city.hospital.model.dto.PatientConsultResponse;
import io.city.hospital.model.dto.PatientDto;
import io.city.hospital.model.dto.SymptomDto;
import io.city.hospital.repository.PatientRepository;
import io.city.hospital.web.error.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientConsultResponse getPatientConsultsAndSymptoms(Long patientId) {
        log.info("Getting consults and symptoms for the patient id {}.", patientId);
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient with number: " + patientId + " not found."));

        List<ConsultDto> consults = patient.getConsults().stream()
                .map(consult -> new ConsultDto(consult.getId(), consult.getDoctor(), consult.getSpecialty().getName()))
                .toList();
        List<SymptomDto> symptoms = patient.getConsults().stream()
                .flatMap(consult -> consult.getSymptoms().stream())
                .map(symptom -> new SymptomDto(symptom.getId(), symptom.getDescription()))
                .toList();

        return new PatientConsultResponse(consults, symptoms);
    }

    public Page<PatientDto> getAllPatients(int page, int size, String sortBy) {
        log.info("Getting all the registered Patients.");
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Patient> patients = patientRepository.findAll(pageable);

        // Convert Page<Patient> to Page<PatientDTO>
        return patients.map(patient -> new PatientDto(
                patient.getId(),
                patient.getName(),
                patient.getAge(),
                patient.getConsults().stream()
                        .map(consult -> new ConsultDto(consult.getId(), consult.getDoctor(), consult.getSpecialty().getName()))
                        .toList()
        ));

    }
}