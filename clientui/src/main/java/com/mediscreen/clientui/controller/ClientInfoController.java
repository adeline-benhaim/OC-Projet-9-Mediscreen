package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.proxies.PatientInfoProxy;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientInfoController {

    @Autowired
    PatientInfoProxy patientInfoProxy;

    @GetMapping("/search/{firstName}/{lastName}")
    public String searchByFirstNameAndLastName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, Model model) {
        try {
            List<PatientBean> patientBeanList = patientInfoProxy.getPatientList(firstName, lastName);
            model.addAttribute("patientBeanList", patientBeanList);
            return "Home";
        } catch (FeignException feignException$NotFound) {
            List<PatientBean> patientBeanList = new ArrayList<>();
            model.addAttribute("patientBeanList", patientBeanList);
            return "Home";
        }
    }

    @PostMapping("/search/{firstName}/{lastName}")
    public String searchPatient(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, Model model) {
        model.addAttribute("firstName",firstName);
        model.addAttribute("lastName",lastName);
        return "redirect:/search/{firstName}/{lastName}";
    }

    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/search/firstName/lastName";
    }

    @GetMapping("/search/{id}")
    public String searchById(@PathVariable("id") int patientId, Model model) {
        try {
            PatientBean patientBean = patientInfoProxy.getPatientById(patientId);
            model.addAttribute("patientBean", patientBean);
            return "Home";
        } catch (FeignException feignException$NotFound) {
            PatientBean patientBean = new PatientBean();
            model.addAttribute("patientBean", patientBean);
            return "Home";
        }
    }

}
