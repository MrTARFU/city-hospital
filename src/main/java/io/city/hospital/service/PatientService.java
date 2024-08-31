package io.city.hospital.service;

import io.city.hospital.model.Patient;
import io.city.hospital.model.dto.ConsultDto;
import io.city.hospital.model.dto.PatientConsultResponse;
import io.city.hospital.model.dto.SymptomDto;
import io.city.hospital.repository.PatientRepository;
import io.city.hospital.web.error.exception.PatientFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientConsultResponse getPatientConsultsAndSymptoms(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientFoundException("Patient with number " + patientId + " not found."));

        List<ConsultDto> consults = patient.getConsults().stream()
                .map(consult -> new ConsultDto(consult.getId(), consult.getDoctor(), consult.getSpecialty().getName()))
                .toList();
        List<SymptomDto> symptoms = patient.getConsults().stream()
                .flatMap(consult -> consult.getSymptoms().stream())
                .map(symptom -> new SymptomDto(symptom.getId(), symptom.getDescription()))
                .toList();

        return new PatientConsultResponse(consults, symptoms);
    }
}