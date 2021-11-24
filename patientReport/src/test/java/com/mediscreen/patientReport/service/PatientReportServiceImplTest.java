package com.mediscreen.patientReport.service;

import com.mediscreen.patientReport.beans.AppointmentBean;
import com.mediscreen.patientReport.beans.PatientBean;
import com.mediscreen.patientReport.proxies.PatientInfoProxy;
import com.mediscreen.patientReport.proxies.PatientNoteProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static com.mediscreen.patientReport.beans.PatientBean.Sex.F;
import static com.mediscreen.patientReport.beans.PatientBean.Sex.M;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientReportServiceImplTest {

    @Mock
    PatientInfoProxy patientInfoProxy;
    @Mock
    PatientNoteProxy patientNoteProxy;
    @InjectMocks
    PatientReportServiceImpl patientReportService;

    @Test
    @DisplayName("Calculate age of patient")
    void calculateAgeTest() {

        //GIVEN
        int patId = 1;
        PatientBean patientBean = PatientBean.builder().patId(1).dob("1978-05-12").build();
        when(patientInfoProxy.getPatientById(patId)).thenReturn(patientBean);

        //WHEN
        int age = patientReportService.calculateAge(patId);

        //THEN
        assertEquals(43, age);
    }

    @Test
    @DisplayName("Count triggers diabetes number in patient note")
    void diabetesTriggersCountTest() {

        //GIVEN
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("fumeur").build();
        AppointmentBean appointmentBean1 = AppointmentBean.builder().patId(1).appointmentId("2").note("anormal").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        appointmentBeanList.add(appointmentBean1);

        //WHEN
        int result = patientReportService.diabetesTriggersCount(Pair.of(appointmentBeanList, 2L));

        //THEN
        assertEquals(2, result);
    }

    @Test
    @DisplayName("Calculate risk level for a man under 30 whose medical file contains 3 trigger terms must return In Danger")
    void calculateRiskLevelManUnder30With3TriggerTermsTest() {

        //GIVEN
        PatientBean patientBean = PatientBean.builder().patId(1).sex(M).dob("2000-05-01").build();
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("fumeur, anormal, poids").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        when(patientInfoProxy.getPatientById(1)).thenReturn(patientBean);
        when(patientNoteProxy.findByPatId(1, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList, 3L));

        //WHEN
        String report = patientReportService.calculateRiskLevel(1);

        //THEN
        assertTrue(report.contains("In Danger"));
        assertTrue(report.contains("(age 21)"));
    }

    @Test
    @DisplayName("Calculate risk level for a man under 30 whose medical file contains 5 trigger terms must return Early onset")
    void calculateRiskLevelManUnder30With5TriggerTermsTest() {

        //GIVEN
        PatientBean patientBean = PatientBean.builder().patId(1).sex(M).dob("2000-05-01").build();
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("Cholestérol, Taille, fumeur, anormal, poids").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        when(patientInfoProxy.getPatientById(1)).thenReturn(patientBean);
        when(patientNoteProxy.findByPatId(1, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList, 3L));

        //WHEN
        String report = patientReportService.calculateRiskLevel(1);

        //THEN
        assertTrue(report.contains("Early onset"));
        assertTrue(report.contains("(age 21)"));
    }

    @Test
    @DisplayName("Calculate risk level for a woman under 30 whose medical file contains 4 trigger terms must return In Danger")
    void calculateRiskLevelWomanUnder30With5TriggerTermsTest() {

        //GIVEN
        PatientBean patientBean = PatientBean.builder().patId(1).sex(F).dob("2000-05-01").build();
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("Cholestérol, Taille, fumeur, anormal").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        when(patientInfoProxy.getPatientById(1)).thenReturn(patientBean);
        when(patientNoteProxy.findByPatId(1, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList, 3L));

        //WHEN
        String report = patientReportService.calculateRiskLevel(1);

        //THEN
        assertTrue(report.contains("In Danger"));
        assertTrue(report.contains("(age 21)"));
    }

    @Test
    @DisplayName("Calculate risk level for a woman under 30 whose medical file contains 7 trigger terms must return Early onset")
    void calculateRiskLevelWomanUnder30With7TriggerTermsTest() {

        //GIVEN
        PatientBean patientBean = PatientBean.builder().patId(1).sex(F).dob("2000-05-01").build();
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("Cholestérol, Taille, fumeur, anormal, anormal, ANORMAL, Anormal").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        when(patientInfoProxy.getPatientById(1)).thenReturn(patientBean);
        when(patientNoteProxy.findByPatId(1, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList, 3L));

        //WHEN
        String report = patientReportService.calculateRiskLevel(1);

        //THEN
        assertTrue(report.contains("Early onset"));
        assertTrue(report.contains("(age 21)"));
    }

    @Test
    @DisplayName("Calculate risk level for a man over 30 whose medical file contains 2 trigger terms must return Borderline")
    void calculateRiskLevelManOver30With2TriggerTermsTest() {

        //GIVEN
        PatientBean patientBean = PatientBean.builder().patId(1).sex(M).dob("1950-05-01").build();
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("fumeur, anormal").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        when(patientInfoProxy.getPatientById(1)).thenReturn(patientBean);
        when(patientNoteProxy.findByPatId(1, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList, 3L));

        //WHEN
        String report = patientReportService.calculateRiskLevel(1);

        //THEN
        assertTrue(report.contains("Borderline"));
        assertTrue(report.contains("(age 71)"));
    }

    @Test
    @DisplayName("Calculate risk level for a woman over 30 whose medical file contains 4 trigger terms must return Borderline")
    void calculateRiskLevelWomanOver30With4TriggerTermsTest() {

        //GIVEN
        PatientBean patientBean = PatientBean.builder().patId(1).sex(F).dob("1950-05-01").build();
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("FUMEUR, ANORMAL, fumeur, anormal").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        when(patientInfoProxy.getPatientById(1)).thenReturn(patientBean);
        when(patientNoteProxy.findByPatId(1, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList, 3L));

        //WHEN
        String report = patientReportService.calculateRiskLevel(1);

        //THEN
        assertTrue(report.contains("Borderline"));
        assertTrue(report.contains("(age 71)"));
    }

    @Test
    @DisplayName("Calculate risk level for a man over 30 whose medical file contains 6 trigger terms must return In Danger")
    void calculateRiskLevelManOver30With6TriggerTermsTest() {

        //GIVEN
        PatientBean patientBean = PatientBean.builder().patId(1).sex(M).dob("1950-05-01").build();
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("Hémoglobine A1C, Microalbumine, Taille, Poidsfumeur, anormal").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        when(patientInfoProxy.getPatientById(1)).thenReturn(patientBean);
        when(patientNoteProxy.findByPatId(1, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList, 3L));

        //WHEN
        String report = patientReportService.calculateRiskLevel(1);

        //THEN
        assertTrue(report.contains("In Danger"));
        assertTrue(report.contains("(age 71)"));
    }

    @Test
    @DisplayName("Calculate risk level for a woman over 30 whose medical file contains 7 trigger terms must return In Danger")
    void calculateRiskLevelWomanOver30With7TriggerTermsTest() {

        //GIVEN
        PatientBean patientBean = PatientBean.builder().patId(1).sex(F).dob("1950-05-01").build();
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("FUMEUR, Hémoglobine A1C, Microalbumine, Taille, Poidsfumeur, anormal").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        when(patientInfoProxy.getPatientById(1)).thenReturn(patientBean);
        when(patientNoteProxy.findByPatId(1, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList, 3L));

        //WHEN
        String report = patientReportService.calculateRiskLevel(1);

        //THEN
        assertTrue(report.contains("In Danger"));
        assertTrue(report.contains("(age 71)"));
    }

    @Test
    @DisplayName("Calculate risk level for a man over 30 whose medical file contains 8 trigger terms must return Early onset")
    void calculateRiskLevelManOver30With8TriggerTermsTest() {

        //GIVEN
        PatientBean patientBean = PatientBean.builder().patId(1).sex(M).dob("1950-05-01").build();
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("Hémoglobine A1C, Microalbumine, Taille, Poidsfumeur, anormal, anormal, fumeur").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        when(patientInfoProxy.getPatientById(1)).thenReturn(patientBean);
        when(patientNoteProxy.findByPatId(1, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList, 3L));

        //WHEN
        String report = patientReportService.calculateRiskLevel(1);

        //THEN
        assertTrue(report.contains("Early onset"));
        assertTrue(report.contains("(age 71)"));
    }

    @Test
    @DisplayName("Calculate risk level for a woman over 30 whose medical file contains 9 trigger terms must return Early onset")
    void calculateRiskLevelWomanOver30With9TriggerTermsTest() {

        //GIVEN
        PatientBean patientBean = PatientBean.builder().patId(1).sex(F).dob("1950-05-01").build();
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("FUMEUR, Hémoglobine A1C, Microalbumine, Taille, Poidsfumeur, anormal, FUMEUR, Hémoglobine A1C, Microalbumine").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        when(patientInfoProxy.getPatientById(1)).thenReturn(patientBean);
        when(patientNoteProxy.findByPatId(1, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList, 3L));

        //WHEN
        String report = patientReportService.calculateRiskLevel(1);

        //THEN
        assertTrue(report.contains("Early onset"));
        assertTrue(report.contains("(age 71)"));
    }

    @Test
    @DisplayName("Calculate risk level for a man under 30 whose medical file contains 1 trigger terms must return None")
    void calculateRiskLevelManUnder30With1TriggerTermsTest() {

        //GIVEN
        PatientBean patientBean = PatientBean.builder().patId(1).sex(M).dob("2000-05-01").build();
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("fumeur").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        when(patientInfoProxy.getPatientById(1)).thenReturn(patientBean);
        when(patientNoteProxy.findByPatId(1, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList, 3L));

        //WHEN
        String report = patientReportService.calculateRiskLevel(1);

        //THEN
        assertTrue(report.contains("None"));
        assertTrue(report.contains("(age 21)"));
    }

    @Test
    @DisplayName("Calculate risk level for a list of patients with same family name")
    void calculateRiskLevelFamilyTest() {

        //GIVEN
        PatientBean patientBean = PatientBean.builder().patId(1).firstName("Pierre").lastName("Dupond").sex(M).dob("2000-05-01").build();
        PatientBean patientBean1 = PatientBean.builder().patId(2).firstName("Paul").lastName("Dupond").sex(M).dob("1950-05-01").build();
        PatientBean patientBean2 = PatientBean.builder().patId(3).firstName("Julie").lastName("Dupond").sex(F).dob("2010-05-01").build();
        List<PatientBean> patientBeans = new ArrayList<>();
        patientBeans.add(patientBean);
        patientBeans.add(patientBean1);
        patientBeans.add(patientBean2);
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("fumeur taille taille").build();
        AppointmentBean appointmentBean1 = AppointmentBean.builder().patId(2).appointmentId("2").note("fumeur taille").build();
        AppointmentBean appointmentBean2 = AppointmentBean.builder().patId(3).appointmentId("3").note("fumeur taille").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        List<AppointmentBean> appointmentBeanList1 = new ArrayList<>();
        appointmentBeanList1.add(appointmentBean1);
        List<AppointmentBean> appointmentBeanList2 = new ArrayList<>();
        appointmentBeanList2.add(appointmentBean2);

        //WHEN
        when(patientInfoProxy.getPatientById(1)).thenReturn(patientBean);
        when(patientInfoProxy.getPatientById(2)).thenReturn(patientBean1);
        when(patientInfoProxy.getPatientById(3)).thenReturn(patientBean2);
        when(patientNoteProxy.findByPatId(1, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList, 2L));
        when(patientNoteProxy.findByPatId(2, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList1, 2L));
        when(patientNoteProxy.findByPatId(3, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList2, 2L));
        when(patientInfoProxy.getAllPatient()).thenReturn(patientBeans);
        List<String> report = patientReportService.calculateRiskLevelFamily("Dupond");

        //THEN
        assertTrue(report.contains("Patient: Pierre Dupond (age 21) diabetes assessment is: In Danger"));
        assertTrue(report.contains("Patient: Paul Dupond (age 71) diabetes assessment is: Borderline"));
        assertTrue(report.contains("Patient: Julie Dupond (age 11) diabetes assessment is: None"));
    }
}
