package com.mediscreen.patientNote.service;

import com.mediscreen.patientNote.config.DataSourceTest;
import com.mediscreen.patientNote.exceptions.AppointmentNotFoundException;
import com.mediscreen.patientNote.model.Appointment;
import com.mediscreen.patientNote.repository.NoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @Mock
    NoteRepository noteRepository;
    @InjectMocks
    NoteServiceImpl noteService;
    @InjectMocks
    DataSourceTest dataSourceTest;

    @Test
    @DisplayName("Get the list of all notes found by patient id")
    void findByPatIdTest() {

        //GIVEN
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("date")));
        Mockito.when(noteRepository.findByPatIdOrderByDateDesc(1, pageable)).thenReturn(dataSourceTest.getAllEmptyPageMocked());
        Page<Appointment> appointmentPage = noteRepository.findByPatIdOrderByDateDesc(1, pageable);
        List<Appointment> appointments = appointmentPage.stream().collect(Collectors.toList());
        Pair<List<Appointment>, Long> pair = Pair.of(appointments, appointmentPage.getTotalElements());



        //WHEN
        Pair<List<Appointment>, Long> listLongPair = noteService.findByPatId(1, pageable);

        //THEN
        assertEquals(listLongPair, pair);
    }

    @Test
    @DisplayName("Get an appointment found by id")
    void findByAppointmentIdTest() {

        //GIVEN
        Optional<Appointment> appointment1 = Optional.ofNullable(Appointment.builder().appointmentId(1).note("note1").build());
        Mockito.when(noteRepository.findByAppointmentId(1)).thenReturn((appointment1));

        //WHEN
        Optional<Appointment> appointmentOptional = noteService.findByAppointmentId(1);

        //THEN
        assertEquals(appointmentOptional, appointment1);
    }

    @Test
    @DisplayName("Try to get an appointment found by unknown id")
    void findByUnknownAppointmentIdTest() {

        //WHEN
        Mockito.when(noteRepository.findByAppointmentId(100)).thenReturn(Optional.empty());

        //THEN
        assertThrows(AppointmentNotFoundException.class, () -> noteService.findByAppointmentId(100));
    }
}