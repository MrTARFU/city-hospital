package io.city.hospital.service;

import io.city.hospital.model.dto.TopSpecialtyResponse;
import io.city.hospital.repository.SpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyService {

        private final SpecialtyRepository specialtyRepository;

        public SpecialtyService(SpecialtyRepository specialtyRepository) {
            this.specialtyRepository = specialtyRepository;
        }

        public List<TopSpecialtyResponse> getTopSpecialties(int minPatients) {
            return specialtyRepository.findSpecialtiesWithMinPatients(minPatients);
        }

}
