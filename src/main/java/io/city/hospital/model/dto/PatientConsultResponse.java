package io.city.hospital.model.dto;

import java.util.List;

public class PatientConsultResponse {
    private List<ConsultDto> consults;
    private List<SymptomDto> symptoms;

    public PatientConsultResponse(List<ConsultDto> consults, List<SymptomDto> symptoms) {
        this.consults = consults;
        this.symptoms = symptoms;
    }

    public List<ConsultDto> getConsults() {
        return consults;
    }

    public void setConsults(List<ConsultDto> consults) {
        this.consults = consults;
    }

    public List<SymptomDto> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<SymptomDto> symptoms) {
        this.symptoms = symptoms;
    }
}

