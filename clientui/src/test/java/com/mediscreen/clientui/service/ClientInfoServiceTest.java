package com.mediscreen.clientui.service;

import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.proxies.PatientInfoProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientInfoServiceTest {

    @Mock
    private PatientInfoProxy patientInfoProxy;
    @InjectMocks
    private ClientInfoServiceImpl clientInfoService;

    @Test
    @DisplayName("Get list of all patients")
    void getAllPatientsTest() {

        //WHEN
        clientInfoService.getAllPatient();

        //THEN
        Mockito.verify(patientInfoProxy, Mockito.times(1)).getAllPatient();
    }

    @Test
    @DisplayName("Get list of patients found by firstname and lastname")
    void getPatientListTest() {

        //WHEN
        clientInfoService.getPatientList("firstName","lastName");

        //THEN
        Mockito.verify(patientInfoProxy, Mockito.times(1)).getPatientList("firstName","lastName");
    }

    @Test
    @DisplayName("Get patient found by id")
    void getPatientByIdTest() {

        //GIVEN
        int id =1;

        //WHEN
        clientInfoService.getPatientById(id);

        //THEN
        Mockito.verify(patientInfoProxy, Mockito.times(1)).getPatientById(id);
    }

    @Test
    @DisplayName("Update a patient")
    void updatePatientTest() {

        //GIVEN
        PatientBean patientBean = PatientBean.builder().lastName("new").firstName("new").phone("0602020202").address("new").dob("1950-11-02").build();

        //WHEN
        clientInfoService.updatePatient(patientBean);

        //THEN
        Mockito.verify(patientInfoProxy, Mockito.times(1)).updatePatient(patientBean);
    }

    @Test
    @DisplayName("Post a patient")
    void postPatientTest() {

        //GIVEN
        String firstName = "fistName";
        String lastName = "lastName";
        String birthdate = "birthdate";
        PatientBean.Sex sex = PatientBean.Sex.F;
        String address = "address";
        String phone = "0630212512";

        //WHEN
        clientInfoService.postPatient(firstName,lastName,birthdate,sex,address,phone);

        //THEN
        Mockito.verify(patientInfoProxy, Mockito.times(1)).postPatient(firstName,lastName,birthdate,sex,address,phone);
    }

}
