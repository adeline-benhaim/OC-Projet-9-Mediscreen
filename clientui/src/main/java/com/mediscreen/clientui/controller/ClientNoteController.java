package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.beans.AppointmentBean;
import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.service.ClientInfoService;
import com.mediscreen.clientui.service.ClientNoteService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClientNoteController {

    @Autowired
    ClientNoteService clientNoteService;
    @Autowired
    ClientInfoService clientInfoService;

    @GetMapping("/note/{id}")
    public String getNoteViewById(@PathVariable("id") String appointmentId, Model model) {
        try {
            AppointmentBean appointmentBean = clientNoteService.getAppointmentById(appointmentId);
            model.addAttribute("appointmentBean", appointmentBean);
            PatientBean patientBean = clientInfoService.getPatientById(appointmentBean.getPatId());
            model.addAttribute("patientBean", patientBean);
            return "NoteView";
        } catch (FeignException feignException$NotFound) {
            return "redirect:/patients";
        }
    }

    @PostMapping("/patHistory/add")
    public String addNewNote(@ModelAttribute AppointmentBean appointmentBean, Model model, BindingResult result) {
        if (!result.hasErrors()) {
            clientNoteService.addNewAppointment(appointmentBean);
            return "redirect:/searchById/" + appointmentBean.getPatId();
        }
        model.addAttribute(appointmentBean);
        return "redirect:/searchById/" + appointmentBean.getPatId();
    }

    @GetMapping("/updateNote/{id}")
    public String getFormToUpdateNote(@PathVariable("id") String appointmentId, Model model) {
        try {
            AppointmentBean appointmentBean = clientNoteService.getAppointmentById(appointmentId);
            model.addAttribute("appointmentBean", appointmentBean);
            PatientBean patientBean = clientInfoService.getPatientById(appointmentBean.getPatId());
            model.addAttribute("patientBean", patientBean);
            return "FormUpdateNote";
        } catch (FeignException feignException$NotFound) {
            return "redirect:/";
        }
    }

    @PostMapping("/patHistory/update")
    public String updateNote(@ModelAttribute AppointmentBean appointmentBean, Model model, BindingResult result) {
        if (!result.hasErrors()) {
            clientNoteService.updateNote(appointmentBean);
            return "redirect:/searchById/" + appointmentBean.getPatId();
        }
        model.addAttribute(appointmentBean);
        return "FormUpdateNote";
    }
}
