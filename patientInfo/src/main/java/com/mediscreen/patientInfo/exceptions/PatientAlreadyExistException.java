package com.mediscreen.patientInfo.exceptions;

public class PatientAlreadyExistException extends RuntimeException{

    public PatientAlreadyExistException(String s) {
        super(s);
    }
}
