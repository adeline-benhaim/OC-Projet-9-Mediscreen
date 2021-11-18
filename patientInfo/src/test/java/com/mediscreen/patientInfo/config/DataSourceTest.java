package com.mediscreen.patientInfo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        Patient patient1 = Patient.builder().firstName("firstname1").lastName("lastname1").dob("1950-01-01").build();
        Patient patient2 = Patient.builder().firstName("firstname1").lastName("lastname1").dob("1950-02-02").build();
        patientMocked.addAll(Arrays.asList(patient1, patient2));
        return patientMocked;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
