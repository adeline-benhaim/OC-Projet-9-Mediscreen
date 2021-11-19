package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.beans.AppointmentBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mediscreen.clientui.constants.UrlProxies.PATIENT_NOTE_PROXY_URL;

@FeignClient(name = "mediscreen-patient-note", url = PATIENT_NOTE_PROXY_URL)
public interface PatientNoteProxy {

    @GetMapping("/patientNote/{patId}")
    Pair<List<AppointmentBean>, Long> findByPatId(@PathVariable("patId") int patId, Pageable pageable);

    @GetMapping("/appointment/{id}")
    AppointmentBean findAppointmentById(@PathVariable("id") String appointmentId);

    @PostMapping("appointment/add")
    AppointmentBean postAppointment(@RequestBody AppointmentBean appointmentBean);

    @PutMapping("/note/update")
    AppointmentBean updateNote(@RequestBody AppointmentBean appointmentBean);
}
