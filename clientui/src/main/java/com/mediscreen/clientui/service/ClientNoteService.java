package com.mediscreen.clientui.service;

import com.mediscreen.clientui.beans.AppointmentBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;

import java.util.List;

public interface ClientNoteService {

    Pair<List<AppointmentBean>, Long> getNotesBean (int patId, Pageable pageable);

    AppointmentBean getAppointmentById(String appointmentId);

    AppointmentBean addNewAppointment(AppointmentBean appointmentBean);

    AppointmentBean updateNote(AppointmentBean appointmentBean);
}
