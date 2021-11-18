package com.mediscreen.patientNote.config;

import com.mediscreen.patientNote.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSourceTest {

    /**
     * Mock Appointment
     */

    List<Appointment> appointmentListMocked = new ArrayList<>();

    public void clearAppointmentListMocked() {
        appointmentListMocked.clear();
    }

    public Page<Appointment> getAllEmptyPageMocked() {
        return Page.empty();
    }

    public List<Appointment> getAllAppointmentMocked() {
        Appointment appointment1 = Appointment.builder().appointmentId(1).patId(1).doctorName("name1").note("note1").build();
        Appointment appointment2 = Appointment.builder().appointmentId(2).patId(1).doctorName("name2").note("note2").build();
        Appointment appointment3 = Appointment.builder().appointmentId(3).patId(1).doctorName("name1").note("note3").build();
        appointmentListMocked.addAll(Arrays.asList(appointment1, appointment2, appointment3));
        return appointmentListMocked;
    }
}
