package com.mediscreen.patientNote.service;

import com.mediscreen.patientNote.exceptions.AppointmentNotFoundException;
import com.mediscreen.patientNote.model.Appointment;
import com.mediscreen.patientNote.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public Optional<Appointment> findByAppointmentId(String appointmentId) {
        logger.info("Get a appointment by id : {} ", appointmentId);
        Optional<Appointment> appointment = noteRepository.findByAppointmentId(appointmentId);
        if (appointment.isPresent()) return appointment;
        logger.error("No appointment found with this  id : {} ", appointmentId);
        throw new AppointmentNotFoundException("No appointment found with this  id : " + appointmentId);
    }

    /**
     * Create a new appointment
     *
     * @param appointment to create
     * @return appointment created
     */
    @Override
    public Appointment createAppointment(Appointment appointment) {
        logger.info("Create a new appointment id {} for patient id : {} ", appointment.getAppointmentId(), appointment.getPatId());
        if (appointment.getPatId() == 0) {
            logger.error("Enable to create appointment without patient id");
            throw new AppointmentNotFoundException("Enable to create appointment without patient id");
        }
        if (appointment.getDoctorName() == null) {
            logger.error("Enable to create appointment without doctor name");
            throw new AppointmentNotFoundException("Enable to create appointment without doctor name");
        }
            appointment.setDate(LocalDateTime.now(ZoneId.of("Europe/Paris")));
            return noteRepository.save(appointment);
        }

        /**
         * Update the history of a patient's note
         *
         * @param appointment's note to update
         * @return appointment with patient's note updated
         */
        @Override
        public Appointment updateNote (Appointment appointment){
            logger.info("Try to update note id : {} , for patient id {} ", appointment.getAppointmentId(), appointment.getPatId());
            Optional<Appointment> appointmentToUpdate = noteRepository.findByAppointmentId(appointment.getAppointmentId());
            if (appointmentToUpdate.isEmpty()) {
                logger.error("Unable to update this note because appointment id : {} doesn't exist", appointment.getAppointmentId());
                throw new AppointmentNotFoundException("Unable to update this note because appointment id : " + appointment.getAppointmentId() + " doesn't exist");
            }
            appointment.setDate(LocalDateTime.now(ZoneId.of("Europe/Paris")));
            appointment.setPatId(appointmentToUpdate.get().getPatId());
            logger.info("Note updated successfully!");
            return noteRepository.save(appointment);
        }

    }
