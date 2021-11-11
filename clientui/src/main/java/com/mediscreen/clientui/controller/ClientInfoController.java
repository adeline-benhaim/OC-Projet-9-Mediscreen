package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.proxies.PatientInfoProxy;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientInfoController {

    @Autowired
    PatientInfoProxy patientInfoProxy;

    @GetMapping("/search/{firstName}/{lastName}")
    public String searchByFirstNameAndLastName(@ModelAttribute PatientBean patientBean, Model model) {
        try {
            List<PatientBean> patientBeanList = patientInfoProxy.getPatientList(patientBean.getFirstName(), patientBean.getLastName());
            model.addAttribute("patientBeanList", patientBeanList);
            return "SearchPatient";
        } catch (FeignException feignException$NotFound) {
            List<PatientBean> patientBeanList = new ArrayList<>();
            model.addAttribute("patientBeanList", patientBeanList);
            return "SearchPatient";
        }
    }

    @PostMapping("/search/{firstname}/{lastname}")
    public String searchPatient(@ModelAttribute PatientBean patientBean) {
        return "redirect:/search/" + patientBean.getFirstName() + "/" + patientBean.getLastName();
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/patients";
    }

    @GetMapping("/patients")
    public String getAllPatients(@ModelAttribute PatientBean patientBean, Model model) {
        try {
            Iterable<PatientBean> allPatientsBeanList = patientInfoProxy.getAllPatient();
            model.addAttribute("allPatientsBeanList", allPatientsBeanList);
            List<PatientBean> patientBeanList = patientInfoProxy.getPatientList(patientBean.getFirstName(), patientBean.getLastName());
            model.addAttribute("patientBeanList", patientBeanList);
            return "ListPatients";
        } catch (FeignException feignException$NotFound) {
            List<PatientBean> patientBeanList = new ArrayList<>();
            model.addAttribute("patientBeanList", patientBeanList);
            return "ListPatients";
        }
    }

    @GetMapping("/searchById/{id}")
    public String searchById(@PathVariable("id") int patId, Model model) {
        try {
            PatientBean patientBean = patientInfoProxy.getPatientById(patId);
            model.addAttribute("patientBean", patientBean);
            return "PatientInfo";
        } catch (FeignException feignException$NotFound) {
            return "redirect:/patients";
        }
    }

    @GetMapping("/updatePatient/{id}")
    public String getFormToUpdatePatient(@PathVariable("id") int patId, Model model) {
        try {
            PatientBean patientBeanToUpdate = patientInfoProxy.getPatientById(patId);
            model.addAttribute("patientBean", patientBeanToUpdate);
            return "FormUpdatePatient";
        } catch (FeignException feignException$NotFound) {
            return "redirect:/patients";
        }
    }

    @PostMapping("/updatePatient")
    public String updatePatient(@ModelAttribute PatientBean patientBean) {
        patientInfoProxy.updatePatient(patientBean);
        return "redirect:/patients";
    }

    @GetMapping("/addPatient")
    public String addPatient(Model model) {
        PatientBean patientBean = new PatientBean();
        model.addAttribute("patientBean", patientBean);
        return "FormNewPatient";
    }

    @PostMapping("/addPatient")
    public String addPatient(@ModelAttribute PatientBean patientBean, Model model, BindingResult result) {
        if (!result.hasErrors()) {
            try {
                PatientBean patientBean1 = patientInfoProxy.postPatient(patientBean.getFirstName(), patientBean.getLastName(), patientBean.getDob(), patientBean.getSex(), patientBean.getAddress(), patientBean.getPhone());
                return "redirect:/searchById/" + patientBean1.getPatId();
            } catch (FeignException e) {
                ObjectError objectError = new ObjectError("error", e.getMessage());
                result.addError(objectError);
                model.addAttribute("patientBean", patientBean);
                return "FormNewPatient";
            }
        }
        return "redirect:/addPatient";
    }

}
