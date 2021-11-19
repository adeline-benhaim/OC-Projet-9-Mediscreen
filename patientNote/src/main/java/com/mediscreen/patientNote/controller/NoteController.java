package com.mediscreen.patientNote.controller;

import com.mediscreen.patientNote.exceptions.AppointmentNotFoundException;
import com.mediscreen.patientNote.model.Appointment;
import com.mediscreen.patientNote.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class NoteController {

    @Autowired
    NoteService noteService;

    /**
     * Get a list of appointment by patient id
     *
     * @param patId id of wanted patient
     * @return a list of appointment found by patient id
     */
    @GetMapping("/patientNote/{patId}")
    public ResponseEntity <Pair<List<Appointment>,Long>> findByPatId(@PathVariable("patId") int patId, Pageable pageable) {
            Pair<List<Appointment>, Long> appointmentList = noteService.findByPatId(patId, pageable);
            return ResponseEntity.ok(appointmentList);
    }

    /**
     * Get an appointment by id
     *
     * @param appointmentId id of wanted appointment
     * @return appointment if exist
     */
    @GetMapping("/appointment/{id}")
    public ResponseEntity<Optional<Appointment>> findAppointmentById(@PathVariable("id") String appointmentId) {
        try {
            Optional<Appointment> appointment = noteService.findByAppointmentId(appointmentId);
            return ResponseEntity.ok(appointment);
        } catch (AppointmentNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Post a new appointment
     *
     * @param appointment to create
     * @return appointment created
     */
    @PostMapping("appointment/add")
    public ResponseEntity<Appointment> postAppointment(@RequestBody Appointment appointment) {
        Appointment appointmentToCreate = noteService.createAppointment(appointment);
        return ResponseEntity.ok(appointmentToCreate);
    }

}

