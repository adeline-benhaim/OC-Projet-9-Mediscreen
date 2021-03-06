package com.mediscreen.patientNote.service;

import com.mediscreen.patientNote.model.Appointment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    /**
     * Find a list of appointment by patient id
     *
     * @param patId id of wanted patient
     * @return a list of appointment found by patient id
     */
    Pair<List<Appointment>, Long> findByPatId(int patId, Pageable pageable);

    /**
     * Find an appointment by id
     *
     * @param appointmentId id of wanted appointment
     * @return appointment if exist
     */
    Optional<Appointment> findByAppointmentId(String appointmentId);

    /**
     * Create a new appointment
     *
     * @param appointment to create
     * @return appointment created
     */
    Appointment createAppointment(Appointment appointment);

    /**
     * Update the history of a patient's note
     *
     * @param appointment's note to update
     * @return appointment with patient's note updated
     */
    Appointment updateNote(Appointment appointment);
}
