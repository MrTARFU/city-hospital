package io.city.hospital.web.controller;

import io.city.hospital.model.dto.ConsultInsertDto;
import io.city.hospital.model.dto.CreateConsultResponseDto;
import io.city.hospital.service.ConsultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.city.hospital.AppConstants.X_ENVIRONMENT;

@Tag(name = "Consult Controller", description = "Allows to register consults")
@RestController
@RequestMapping("/api")
public class ConsultController {

    private final ConsultService consultService;

    public ConsultController(ConsultService consultService) {
        this.consultService = consultService;
    }

    @Operation(description = "Allows to register new consults that happen in the hospital", parameters = {
            @Parameter(in = ParameterIn.HEADER,
                    description = "Environment",
                    name = X_ENVIRONMENT,
                    content = @Content(schema = @Schema(type = "string")))
    })
    @PostMapping("/consults")
    public ResponseEntity<CreateConsultResponseDto> createConsult(@RequestBody ConsultInsertDto consultInsertDto) {
        CreateConsultResponseDto createConsultResponseDto = consultService.createConsult(consultInsertDto);
        return new ResponseEntity<>(createConsultResponseDto, HttpStatus.CREATED);
    }
}
