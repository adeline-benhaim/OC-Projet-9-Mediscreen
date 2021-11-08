package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.mediscreen.clientui.constants.UrlProxies.PATIENT_INFO_PROXY_URL;

@FeignClient(name = "mediscreen-patient-info", url = PATIENT_INFO_PROXY_URL)
public interface PatientInfoProxy {

    @GetMapping("/patientInfo")
    Iterable<PatientBean> getAllPatient();

    @GetMapping("/patientInfo/search/{firstName}/{lastName}")
    List<PatientBean> getPatientList(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName);

    @GetMapping("/patientInfo/search/{id}")
    PatientBean getPatientById (@PathVariable("id") int id);
}
