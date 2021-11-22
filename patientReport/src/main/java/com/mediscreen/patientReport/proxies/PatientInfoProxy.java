package com.mediscreen.patientReport.proxies;

import com.mediscreen.patientReport.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mediscreen-patient-info", url="${patient.info.proxy.url}")
public interface PatientInfoProxy {

    /**
     * Find all patients with info
     *
     * @return a list with all patients with info
     */
    @GetMapping("/patientInfo")
    Iterable<PatientBean> getAllPatient();

    /**
     * Find patients by first and last name
     *
     * @param firstName of wanted patient
     * @param lastName  of wanted patient
     * @return a list of patients whose first and last name are identical to those sought
     */
    @GetMapping("/patientInfo/search/{firstName}/{lastName}")
    List<PatientBean> getPatientList(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName);

    /**
     * Find a patient by id
     *
     * @param id of wanted patient
     * @return patient found
     */
    @GetMapping("/patientInfo/search/{id}")
    PatientBean getPatientById (@PathVariable("id") int id);

}
