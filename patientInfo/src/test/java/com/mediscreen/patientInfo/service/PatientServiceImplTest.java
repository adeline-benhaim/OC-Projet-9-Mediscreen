package com.mediscreen.patientInfo.service;

import com.mediscreen.patientInfo.config.DataSourceTest;
import com.mediscreen.patientInfo.exceptions.PatientNotFoundException;
import com.mediscreen.patientInfo.model.Patient;
import com.mediscreen.patientInfo.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceImplTest {

    @Mock
    PatientRepository patientRepository;
    @InjectMocks
    DataSourceTest dataSourceTest;
    @InjectMocks
    PatientServiceImpl patientService;

    @BeforeEach
    void clear() {
        dataSourceTest.clearPatientListMocked();
    }

    @Test
    @DisplayName("Get the list of all the patients present in the data source")
    void getAllPatientsTest() {

        //GIVEN
        when(patientRepository.findAllByOrderByLastNameAsc()).thenReturn(dataSourceTest.getAllPatientsMocked());

        //WHEN
        Iterable<Patient> patientIterable = patientService.getAllPatients();

        //THEN
        assertEquals(patientIterable, dataSourceTest.getPatientMocked());
    }

    @Test
    @DisplayName("Get a list of existing patients found by first and last name")
    void getPatientListByFistNameAndLastNameTest() {

        //GIVEN
        Mockito.when(patientRepository.findByFirstNameAndLastName("firstname1", "lastname1")).thenReturn(dataSourceTest.getAllPatientsMocked());

        //WHEN
        patientService.getPatientListByFistNameAndLastName("firstname1", "lastname1");

        //THEN
        Mockito.verify(patientRepository, Mockito.times(1)).findByFirstNameAndLastName("firstname1", "lastname1");
    }

    @Test
    @DisplayName("Get a list of patients with unknown first and last name throw a patientNotFoundException")
    void getPatientListByUnknownFistNameAndLastNameTest() {

        //GIVEN
        Mockito.when(patientRepository.findByFirstNameAndLastName("firstname2", "lastname2")).thenReturn(new ArrayList<>());

        //THEN
        assertThrows(PatientNotFoundException.class, () -> patientService.getPatientListByFistNameAndLastName("firstname2", "lastname2"));
    }

    @Test
    @DisplayName("Get an existing patient found by id")
    void getPatientByIdTest() {

        //GIVEN
        Mockito.when(patientRepository.findById(0)).thenReturn(java.util.Optional.ofNullable(dataSourceTest.getAllPatientsMocked().get(0)));

        //WHEN
        patientService.getPatientById(0);

        //THEN
        Mockito.verify(patientRepository, Mockito.times(1)).findById(0);
    }

    @Test
    @DisplayName("Get a patient with an unknown id")
    void getPatientByUnknownIdTest() {

        //GIVEN
        Mockito.when(patientRepository.findById(2)).thenReturn(Optional.empty());

        //THEN
        assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(2));
    }
}
