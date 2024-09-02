package io.city.hospital.web.controller;

import io.city.hospital.metrics.MetricUtils;
import io.city.hospital.metrics.MetricsConstants;
import io.city.hospital.model.dto.TopSpecialtyResponse;
import io.city.hospital.service.SpecialtyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.city.hospital.AppConstants.X_ENVIRONMENT;

@Tag(name = "Specialty Controller", description = "Allows to know the top specialties")
@RestController
@RequestMapping("/api")
public class SpecialtyController {

    private final Logger log = LoggerFactory.getLogger(SpecialtyController.class);
    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @Operation(description = "Allows to know the top specialties that the hospital has to offer", parameters = {
            @Parameter(in = ParameterIn.HEADER,
                    description = "Environment",
                    name = X_ENVIRONMENT,
                    content = @Content(schema = @Schema(type = "string")))
    })
    @GetMapping("/specialties/top")
    public ResponseEntity<List<TopSpecialtyResponse>> getTopSpecialties(@RequestParam(name = "minPatients") int minPatients) {
        log.info("Request to know which specialties have a minimum of {} patients", minPatients);
        MetricUtils.incrementMetric(MetricsConstants.GET_TOP_SPECIALTIES);
        List<TopSpecialtyResponse> specialties = specialtyService.getTopSpecialties(minPatients);
        return new ResponseEntity<>(specialties, HttpStatus.OK);
    }
}
