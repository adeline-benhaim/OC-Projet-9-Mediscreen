package com.mediscreen.clientui.service;

import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.proxies.PatientInfoProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientInfoServiceImpl implements ClientInfoService {

    @Autowired
    PatientInfoProxy patientInfoProxy;

    /**
     * Find all patients with info
     *
     * @return a list with all patients with info
     */
    @Override
    public Iterable<PatientBean> getAllPatient() {
        return patientInfoProxy.getAllPatient();
    }

    /**
     * Find patients by first and last name
     *
     * @param firstName of wanted patient
     * @param lastName  of wanted patient
     * @return a list of patients whose first and last name are identical to those sought
     */
    @Override
    public List<PatientBean> getPatientList(String firstName, String lastName) {
        return patientInfoProxy.getPatientList(firstName,lastName);
    }

    /**
     * Find a patient by id
     *
     * @param id of wanted patient
     * @return patient found
     */
    @Override
    public PatientBean getPatientById(int id) {
        return patientInfoProxy.getPatientById(id);
    }

    /**
     * Update a patient
     *
     * @param patientBean information of patient to update
     * @return patient updated
     */
    @Override
    public PatientBean updatePatient(PatientBean patientBean) {
        return patientInfoProxy.updatePatient(patientBean);
    }

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
    @Override
    public PatientBean postPatient(String firstName, String lastName, String birthdate, PatientBean.Sex sex, String address, String phone) {
        return patientInfoProxy.postPatient(firstName,lastName,birthdate,sex,address,phone);
    }
}
