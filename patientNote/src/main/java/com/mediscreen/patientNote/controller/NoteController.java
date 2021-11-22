package com.mediscreen.patientNote.controller;

import com.mediscreen.patientNote.exceptions.AppointmentNotFoundException;
import com.mediscreen.patientNote.model.Appointment;
import com.mediscreen.patientNote.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api("API for CRUD operations for patientNote")
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
    @ApiOperation("Get a list of appointment by patient id")
    @GetMapping("/patientNote/{patId}")
    public ResponseEntity<Pair<List<Appointment>, Long>> findByPatId(@PathVariable("patId") int patId, Pageable pageable) {
        Pair<List<Appointment>, Long> appointmentList = noteService.findByPatId(patId, pageable);
        return ResponseEntity.ok(appointmentList);
    }

    /**
     * Get an appointment by id
     *
     * @param appointmentId id of wanted appointment
     * @return appointment if exist
     */
    @ApiOperation("Get an appointment by id")
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
    @ApiOperation("Post a new appointment")
    @PostMapping("/appointment/add")
    public ResponseEntity<Appointment> postAppointment(@RequestBody Appointment appointment) {
        try {
            Appointment appointmentToCreate = noteService.createAppointment(appointment);
            return ResponseEntity.ok(appointmentToCreate);
        } catch (AppointmentNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Put the history of a patient's note
     *
     * @param appointment's note to update
     * @return appointment with patient's note updated
     */
    @ApiOperation("Put the history of a patient's note")
    @PutMapping("/note/update")
    public ResponseEntity<Appointment> updateNote(@RequestBody Appointment appointment) {
        try {
            Appointment appointmentToUpdate = noteService.updateNote(appointment);
            return ResponseEntity.ok(appointmentToUpdate);
        } catch (AppointmentNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}

