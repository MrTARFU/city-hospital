package io.city.hospital.repository;

import io.city.hospital.model.Specialty;
import io.city.hospital.model.dto.TopSpecialtyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

    @Query("SELECT new io.city.hospital.model.dto.TopSpecialtyResponse(s.name, COUNT(DISTINCT c.patient)) " +
            "FROM Specialty s JOIN s.consults c " +
            "GROUP BY s.name " +
            "HAVING COUNT(DISTINCT c.patient) > :minPatients")
    List<TopSpecialtyResponse> findSpecialtiesWithMinPatients(@Param("minPatients") int minPatients);
}

