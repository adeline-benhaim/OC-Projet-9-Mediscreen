package com.mediscreen.patientNote.controller;

import com.mediscreen.patientNote.exceptions.AppointmentNotFoundException;
import com.mediscreen.patientNote.model.Appointment;
import com.mediscreen.patientNote.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class NoteController {

    @Autowired
    NoteService noteService;

    @GetMapping("/patientNote/{patId}")
    public ResponseEntity <Pair<List<Appointment>,Long>> findByPatId(@PathVariable("patId") int patId, Pageable pageable) {
            Pair<List<Appointment>, Long> appointmentList = noteService.findByPatId(patId, pageable);
            return ResponseEntity.ok(appointmentList);
    }

    @GetMapping("/appointment/{id}")
    public ResponseEntity<Optional<Appointment>> findAppointmentById(@PathVariable("id") int appointmentId) {
        try {
            Optional<Appointment> appointment = noteService.findByAppointmentId(appointmentId);
            return ResponseEntity.ok(appointment);
        } catch (AppointmentNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}

