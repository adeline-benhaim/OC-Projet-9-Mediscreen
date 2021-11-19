package com.mediscreen.clientui.service;

import com.mediscreen.clientui.beans.AppointmentBean;
import com.mediscreen.clientui.proxies.PatientNoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientNoteServiceImpl implements ClientNoteService {

    @Autowired
    PatientNoteProxy patientNoteProxy;

    @Override
    public Pair<List<AppointmentBean>, Long> getNotesBean (int patId, Pageable pageable) {
        return patientNoteProxy.findByPatId(patId, pageable);
    }

    @Override
    public AppointmentBean getAppointmentById(String appointmentId) {
        return patientNoteProxy.findAppointmentById(appointmentId);
    }
    @Override
    public AppointmentBean addNewAppointment(AppointmentBean appointmentBean) {
        return patientNoteProxy.postAppointment(appointmentBean);
    }
}
