package com.mediscreen.patientReport.proxies;

import com.mediscreen.patientReport.beans.AppointmentBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mediscreen-patient-note", url="${patient.note.proxy.url}")

public interface PatientNoteProxy {

    @GetMapping("/patientNote/{patId}")
    Pair<List<AppointmentBean>, Long> findByPatId(@PathVariable("patId") int patId, Pageable pageable);

}
