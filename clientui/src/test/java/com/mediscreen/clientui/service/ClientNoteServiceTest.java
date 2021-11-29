package com.mediscreen.clientui.service;

import com.mediscreen.clientui.beans.AppointmentBean;
import com.mediscreen.clientui.proxies.PatientNoteProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class ClientNoteServiceTest {

    @Mock
    private PatientNoteProxy patientNoteProxy;
    @InjectMocks
    private ClientNoteServiceImpl clientNoteService;

    @Test
    @DisplayName("Get list of notes beans by patient id")
    void getNotesBeanTest() {

        //GIVEN
        int id = 1;

        //WHEN
        clientNoteService.getNotesBean(id, Pageable.unpaged());

        //THEN
        Mockito.verify(patientNoteProxy, Mockito.times(1)).findByPatId(id, Pageable.unpaged());
    }

    @Test
    @DisplayName("Get appointment by id")
    void getAppointmentByIdTest() {

        //GIVEN
        String appointmentId = "1";

        //WHEN
        clientNoteService.getAppointmentById(appointmentId);

        //THEN
        Mockito.verify(patientNoteProxy, Mockito.times(1)).findAppointmentById(appointmentId);
    }

    @Test
    @DisplayName("Add new appointment")
    void addNewAppointmentTest() {

        //GIVEN
        AppointmentBean appointmentBean = new AppointmentBean();

        //WHEN
        clientNoteService.addNewAppointment(appointmentBean);

        //THEN
        Mockito.verify(patientNoteProxy, Mockito.times(1)).postAppointment(appointmentBean);
    }

    @Test
    @DisplayName("Update an appointment")
    void updateAnAppointmentTest() {

        //GIVEN
        AppointmentBean appointmentBean = new AppointmentBean();

        //WHEN
        clientNoteService.updateNote(appointmentBean);

        //THEN
        Mockito.verify(patientNoteProxy, Mockito.times(1)).updateNote(appointmentBean);
    }

}
