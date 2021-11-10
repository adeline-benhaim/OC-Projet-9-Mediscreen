package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.mediscreen.clientui.constants.UrlProxies.PATIENT_INFO_PROXY_URL;

@FeignClient(name = "mediscreen-patient-info", url = PATIENT_INFO_PROXY_URL)
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

    @PutMapping("/patientInfo/update")
    PatientBean updatePatient(@RequestBody PatientBean patientBean );
}
