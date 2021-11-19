package com.mediscreen.patientNote.repository;

import com.mediscreen.patientNote.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends MongoRepository<Appointment, Integer> {

    /**
     * Find a list of appointment by patient id
     *
     * @param patId id of wanted patient
     * @return a list of appointment found by patient id sort by date desc
     */
    Page<Appointment> findByPatIdOrderByDateDesc(int patId, Pageable pageable);

    /**
     * Find an appointment by id
     *
     * @param appointmentId id of wanted appointment
     * @return appointment if exist
     */
    Optional<Appointment> findByAppointmentId(String appointmentId);

}
