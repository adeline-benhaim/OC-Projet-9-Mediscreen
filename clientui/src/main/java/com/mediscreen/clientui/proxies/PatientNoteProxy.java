package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.beans.AppointmentBean;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
import java.util.Optional;

import static com.mediscreen.clientui.constants.UrlProxies.PATIENT_NOTE_PROXY_URL;

@FeignClient(name = "mediscreen-patient-note", url = PATIENT_NOTE_PROXY_URL)
public interface PatientNoteProxy {

    @GetMapping("/patientNote/{patId}")
    Pair<List<AppointmentBean>,Long> findByPatId(@PathVariable("patId") int patId, Pageable pageable);

    @GetMapping("/appointment/{id}")
    AppointmentBean findAppointmentById(@PathVariable("id") int appointmentId);
}
