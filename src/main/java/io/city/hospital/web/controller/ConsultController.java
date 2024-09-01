package io.city.hospital.web.controller;

import io.city.hospital.model.dto.ConsultInsertDto;
import io.city.hospital.model.dto.CreateConsultResponseDto;
import io.city.hospital.service.ConsultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConsultController {

    private final ConsultService consultService;

    public ConsultController(ConsultService consultService) {
        this.consultService = consultService;
    }

    @PostMapping("/consults")
    public ResponseEntity<CreateConsultResponseDto> createConsult(@RequestBody ConsultInsertDto consultInsertDto) {
        CreateConsultResponseDto createConsultResponseDto = consultService.createConsult(consultInsertDto);
        return new ResponseEntity<>(createConsultResponseDto, HttpStatus.CREATED);
    }
}
