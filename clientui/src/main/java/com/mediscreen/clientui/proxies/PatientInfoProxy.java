package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Update a patient
     *
     * @param patientBean information of patient to update
     * @return patient updated
     */
    @PutMapping("/patientInfo/update")
    PatientBean updatePatient(@RequestBody PatientBean patientBean );

    /**
     * Create a new patient
     *
     * @param firstName of patient to create
     * @param lastName of patient to create
     * @param birthdate of patient to create
     * @param sex of patient to create
     * @param address of patient to create not required
     * @param phone of patient to create not required
     * @return new patient created
     */
    @PostMapping("/patient/add")
    PatientBean postPatient(@RequestParam("firstName")String firstName,
                            @RequestParam("lastName")String lastName,
                            @RequestParam("dob")String birthdate,
                            @RequestParam("sex")PatientBean.Sex sex,
                            @RequestParam(value = "address", required = false)String address,
                            @RequestParam(value = "phone", required = false)String phone);
}
