package com.mediscreen.patientNote.service;

import com.mediscreen.patientNote.exceptions.AppointmentNotFoundException;
import com.mediscreen.patientNote.model.Appointment;
import com.mediscreen.patientNote.repository.NoteRepository;
import org.bson.codecs.MapCodecProvider;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {
    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);

    @Autowired
    NoteRepository noteRepository;

    /**
     * Find a list of appointment by patient id
     *
     * @param patId id of wanted patient
     * @return a list of appointment found by patient id
     */
    @Override
    public Pair<List<Appointment>, Long> findByPatId(int patId, Pageable pageable) {
        logger.info("Try to get a appointment by patient id : {} ", patId);
        Page<Appointment> appointmentPage = noteRepository.findByPatIdOrderByDateDesc(patId, pageable);
        List<Appointment> appointments = appointmentPage.stream().collect(Collectors.toList());
        if (appointments.isEmpty()) {
            logger.error("No appointment found with this patient id : {} ", patId);
        }
        logger.info("Get a appointment by patient id : {} ", patId);
        return Pair.of(appointments, appointmentPage.getTotalElements());
    }

    /**
     * Find an appointment by id
     *
     * @param appointmentId id of wanted appointment
     * @return appointment if exist
     */
    @Override
    public Optional<Appointment> findByAppointmentId(int appointmentId) {
        logger.info("Get a appointment by id : {} ", appointmentId);
        Optional<Appointment> appointment = noteRepository.findByAppointmentId(appointmentId);
        if (appointment.isPresent()) return appointment;
        logger.error("No appointment found with this  id : {} ", appointmentId);
        throw new AppointmentNotFoundException("");

    }


}
