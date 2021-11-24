package com.mediscreen.clientui.service;

import com.mediscreen.clientui.beans.PatientBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClientInfoService {

    /**
     * Find all patients with info
     *
     * @return a list with all patients with info
     */
    Iterable<PatientBean> getAllPatient();

    /**
     * Find patients by first and last name
     *
     * @param firstName of wanted patient
     * @param lastName  of wanted patient
     * @return a list of patients whose first and last name are identical to those sought
     */
    List<PatientBean> getPatientList(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName);

    /**
     * Find a patient by id
     *
     * @param id of wanted patient
     * @return patient found
     */
    PatientBean getPatientById(@PathVariable("id") int id);

    /**
     * Update a patient
     *
     * @param patientBean information of patient to update
     * @return patient updated
     */
    PatientBean updatePatient(@RequestBody PatientBean patientBean);

    /**
     * Create a new patient
     *
     * @param firstName of patient to create
     * @param lastName  of patient to create
     * @param birthdate of patient to create
     * @param sex       of patient to create
     * @param address   of patient to create not required
     * @param phone     of patient to create not required
     * @return new patient created
     */
    PatientBean postPatient(@RequestParam("firstName") String firstName,
                            @RequestParam("lastName") String lastName,
                            @RequestParam("dob") String birthdate,
                            @RequestParam("sex") PatientBean.Sex sex,
                            @RequestParam(value = "address", required = false) String address,
                            @RequestParam(value = "phone", required = false) String phone);
}