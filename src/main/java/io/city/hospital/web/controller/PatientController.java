package io.city.hospital.web.controller;

import io.city.hospital.model.dto.PatientConsultResponse;
import io.city.hospital.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.city.hospital.AppConstants.X_ENVIRONMENT;

@Tag(name = "Patient Controller", description = "Allows to know information about the patient")
@RestController
@RequestMapping("/api/patients")
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
    @GetMapping("/consults/{id}")
    public ResponseEntity<PatientConsultResponse> getPatientConsultsAndSymptoms(@PathVariable("id") Long id) {
        PatientConsultResponse response = patientService.getPatientConsultsAndSymptoms(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
