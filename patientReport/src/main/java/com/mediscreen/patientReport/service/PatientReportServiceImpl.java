package com.mediscreen.patientReport.service;

import com.mediscreen.patientReport.beans.AppointmentBean;
import com.mediscreen.patientReport.beans.PatientBean;
import com.mediscreen.patientReport.model.DiabetesTriggers;
import com.mediscreen.patientReport.proxies.PatientInfoProxy;
import com.mediscreen.patientReport.proxies.PatientNoteProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.mediscreen.patientReport.beans.PatientBean.Sex.F;
import static com.mediscreen.patientReport.beans.PatientBean.Sex.M;
import static com.mediscreen.patientReport.constants.RiskLevel.*;

@Service
public class PatientReportServiceImpl implements PatientReportService {
    private static final Logger logger = LoggerFactory.getLogger(PatientReportService.class);

    @Autowired
    PatientInfoProxy patientInfoProxy;
    @Autowired
    PatientNoteProxy patientNoteProxy;

    /**
     * Calculate age of patient
     *
     * @param patId id of the patient whose age is sought
     * @return age of patient found by id
     */
    public int calculateAge(int patId) {
        logger.info("Try to calculate age for patient id : {} ", patId);
        String birthdate = patientInfoProxy.getPatientById(patId).getDob();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate bod = LocalDate.parse(birthdate, formatter);
        logger.info("Calculate age for patient id : {} ", patId);
        return Period.between(bod, LocalDate.now()).getYears();
    }

    /**
     * Counts the number of occurrences without duplicate of trigger terms found in a list of notes
     *
     * @param patientNoteProxies list of appointments which contain the list of notes
     * @return the number of occurrences without duplicate found
     */
    public int diabetesTriggersCount(Pair<List<AppointmentBean>, Long> patientNoteProxies) {
        logger.info("Count diabetes triggers");
        List<String> countWithDuplicate = new ArrayList();
        for (AppointmentBean note : patientNoteProxies.getFirst()) {
            for (String trigger : DiabetesTriggers.TriggersList) {
                if (note.getNote().toLowerCase(Locale.ROOT).contains(trigger.toLowerCase(Locale.ROOT))) {
                    countWithDuplicate.add(trigger);
                }
            }
        }
        Set<String> mySet = new HashSet<String>(countWithDuplicate);
        List<String> countWithoutDuplicate = new ArrayList<String>(mySet);
        return countWithoutDuplicate.size();
    }

    /**
     * Create a report that includes patient demographics, as well as their diabetes risk level
     *
     * @param patId id of the patient whose report is sought
     * @return a string with report by patient id
     */
    @Override
    public String calculateRiskLevel(int patId) {
        logger.info("Calculate risk level for patient id : {} ", patId);
        PatientBean patientBean = patientInfoProxy.getPatientById(patId);
        Pair<List<AppointmentBean>, Long> appointmentBeanList = patientNoteProxy.findByPatId(patId, Pageable.unpaged());
        int nbDiabetesTriggers = diabetesTriggersCount(appointmentBeanList);
        PatientBean.Sex sex = patientBean.getSex();
        int age = calculateAge(patId);
        String diabetesAssessment = null;
        if (age < 30) {
            logger.info("Calculate risk level for patient < 30 ");
            if (sex == M) {
                logger.info("Calculate risk level for man");
                if (nbDiabetesTriggers >= 3 & nbDiabetesTriggers < 5) {
                    diabetesAssessment = IN_DANGER;
                } else if (nbDiabetesTriggers >= 5) {
                    diabetesAssessment = EARLY_ONSET;
                }
            } else if (sex == F) {
                logger.info("Calculate risk level for woman");
                if (nbDiabetesTriggers >= 4 & nbDiabetesTriggers < 7) {
                    diabetesAssessment = IN_DANGER;
                } else if (nbDiabetesTriggers >= 7) {
                    diabetesAssessment = EARLY_ONSET;
                }
            }
        } else {
            logger.info("Calculate risk level for patient > 30 ");
            if (nbDiabetesTriggers >= 2 & nbDiabetesTriggers < 6) {
                diabetesAssessment = BORDERLINE;
            } else if (nbDiabetesTriggers >= 6 & nbDiabetesTriggers < 8) {
                diabetesAssessment = IN_DANGER;
            } else if (nbDiabetesTriggers >= 8) {
                diabetesAssessment = EARLY_ONSET;
            }
        }
        if (diabetesAssessment == null) {
            diabetesAssessment = NONE;
        }
        return "Patient: " + patientBean.getFirstName() + " " + patientBean.getLastName() + " (age " + age + ") diabetes assessment is: " + diabetesAssessment;
    }

    /**
     * Create a list of reports with all patients with the same lastname that includes patient demographics, as well as their diabetes risk level
     *
     * @param lastName of the patient's list whose report is sought
     * @return a list with with all patients found with the same lastname
     */
    @Override
    public List<String> calculateRiskLevelFamily(String lastName) {
        logger.info("Calculate risk level for family name : " + lastName);
        Iterable<PatientBean> patientIt = patientInfoProxy.getAllPatient();
        List<PatientBean> patientList = new ArrayList<>();
        for (PatientBean patient : patientIt) {
            if (patient.getLastName().equalsIgnoreCase(lastName)) {
                patientList.add(patient);
            }
        }
        List<String> riskFamily = new ArrayList<>();
        for (PatientBean patientBean : patientList) {
            String calculateRiskLevel = calculateRiskLevel(patientBean.getPatId());
            riskFamily.add(calculateRiskLevel);
        }
        return riskFamily;
    }
}
