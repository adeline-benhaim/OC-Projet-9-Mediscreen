package com.mediscreen.patientInfo.config;

import com.mediscreen.patientInfo.model.Patient;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class DataSourceTest {

    /**
     * Mock Patient
     */
    List<Patient> patientMocked = new ArrayList<>();

    public void clearPatientListMocked() {
        patientMocked.clear();
    }

    public List<Patient> getAllPatientsMocked() {
        Patient patient1 = Patient.builder().firstName("firstname1").lastName("lastname1").build();
        Patient patient2 = Patient.builder().firstName("firstname1").lastName("lastname1").build();
        patientMocked.addAll(Arrays.asList(patient1, patient2));
        return patientMocked;
    }
}
