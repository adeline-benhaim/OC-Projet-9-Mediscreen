package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.service.ClientInfoService;
import com.mediscreen.clientui.service.ClientReportService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ClientReportController {

    @Autowired
    ClientReportService clientReportService;
    @Autowired
    ClientInfoService clientInfoService;

    @GetMapping("/assess/{id}")
    public String getReportById(@PathVariable("id") int patId, Model model) {
        try {
            PatientBean patientBean = clientInfoService.getPatientById(patId);
            model.addAttribute("patientBean", patientBean);
            String assessById = clientReportService.postAssessById(patId);
            model.addAttribute("assessById", assessById);
            List<String> assessByFamilyName = clientReportService.postAssessByName(patientBean.getLastName());
            model.addAttribute("assessByFamilyName", assessByFamilyName);
            return "Report";
        } catch (FeignException e) {
            return "redirect:/patients";
        }
    }
}
