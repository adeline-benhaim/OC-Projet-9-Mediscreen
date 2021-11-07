package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "mediscreen-patient-info", url = "localhost:8081")
public interface PatientInfoProxy {

    @GetMapping("/patientInfo")
    Iterable<PatientBean> getAllPatient();

    @GetMapping("/patientInfo/search/{firstName}/{lastName}")
    List<PatientBean> getPatientList(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName);

    @GetMapping("/patientInfo/search/{id}")
    PatientBean getPatientById (@PathVariable("id") int id);
}
