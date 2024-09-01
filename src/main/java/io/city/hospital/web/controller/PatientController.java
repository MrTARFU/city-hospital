package io.city.hospital.web.controller;

import io.city.hospital.model.dto.PatientConsultResponse;
import io.city.hospital.model.dto.PatientDto;
import io.city.hospital.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.city.hospital.AppConstants.X_ENVIRONMENT;

@Tag(name = "Patient Controller", description = "Allows to know information about the patient")
@RestController
@RequestMapping("/api")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(description = "Allows to know all the consults and the symptoms that the patient has had", parameters = {
            @Parameter(in = ParameterIn.HEADER,
                    description = "Environment",
                    name = X_ENVIRONMENT,
                    content = @Content(schema = @Schema(type = "string")))
    })
    @GetMapping("/patients/consults/{id}")
    public ResponseEntity<PatientConsultResponse> getPatientConsultsAndSymptoms(@PathVariable("id") Long id) {
        PatientConsultResponse response = patientService.getPatientConsultsAndSymptoms(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(description = "Allows to know data from all the patients with pagination, filtering and sorting", parameters = {
            @Parameter(in = ParameterIn.HEADER,
                    description = "Environment",
                    name = X_ENVIRONMENT,
                    content = @Content(schema = @Schema(type = "string")))
    })
    @GetMapping("/patients")
    public ResponseEntity<Page<PatientDto>> getAllPatients(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name= "size", defaultValue = "10") int size,
            @RequestParam(name= "sortBy", defaultValue = "name") String sortBy) {
        Page<PatientDto> patients = patientService.getAllPatients(page, size, sortBy);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }
}
