package io.city.hospital.service;

import io.city.hospital.model.Consult;
import io.city.hospital.model.Patient;
import io.city.hospital.model.Specialty;
import io.city.hospital.model.dto.ConsultInsertDto;
import io.city.hospital.model.dto.CreateConsultResponseDto;
import io.city.hospital.repository.ConsultRepository;
import io.city.hospital.repository.PatientRepository;
import io.city.hospital.repository.SpecialtyRepository;
import io.city.hospital.web.error.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ConsultService {

    private final Logger log = LoggerFactory.getLogger(ConsultService.class);

    private final ConsultRepository consultRepository;
    private final PatientRepository patientRepository;
    private final SpecialtyRepository specialtyRepository;

    public ConsultService(ConsultRepository consultRepository,
                          PatientRepository patientRepository,
                          SpecialtyRepository specialtyRepository) {
        this.consultRepository = consultRepository;
        this.patientRepository = patientRepository;
        this.specialtyRepository = specialtyRepository;
    }

    public CreateConsultResponseDto createConsult(ConsultInsertDto consultInsertDto) {
        log.info("Creation of Consult for Doctor {}", consultInsertDto.getDoctor());
        Patient patient = patientRepository.findById(consultInsertDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient with number: "+ consultInsertDto.getPatientId() + " not found."));
        Specialty specialty = specialtyRepository.findById(consultInsertDto.getSpecialtyId())
                .orElseThrow(() -> new ResourceNotFoundException("Specialty with id: " + consultInsertDto.getSpecialtyId() + " not found."));

        Consult consult = new Consult();
        consult.setDoctor(consultInsertDto.getDoctor());
        consult.setSpecialty(specialty);
        consult.setPatient(patient);

        var a = consultRepository.save(consult);

        CreateConsultResponseDto createConsultResponseDto = new CreateConsultResponseDto();
        createConsultResponseDto.setId(a.getId());
        createConsultResponseDto.setDoctor(a.getDoctor());
        createConsultResponseDto.setPatientName(a.getPatient().getName());
        createConsultResponseDto.setSpecialtyName(a.getSpecialty().getName());

        return createConsultResponseDto;
    }
}

