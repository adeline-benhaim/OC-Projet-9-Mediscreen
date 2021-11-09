package com.mediscreen.patientInfo.exceptions;

public class PatientNotFoundException extends RuntimeException{

    public PatientNotFoundException(String s) {
        super(s);
    }
}
