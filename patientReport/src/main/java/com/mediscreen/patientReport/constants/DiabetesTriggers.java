package com.mediscreen.patientReport.constants;

import java.util.regex.Pattern;

public class DiabetesTriggers {

    public static final Pattern TRIGGERS = Pattern.compile("Hémoglobine A1C|Microalbumine|Taille|Poids|Fumeur|Anormal|Cholestérol|Vertige|Rechute|Réaction|Anticorps",Pattern.CASE_INSENSITIVE);
}

