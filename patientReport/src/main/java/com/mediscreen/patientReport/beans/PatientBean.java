package com.mediscreen.patientReport.beans;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
public class PatientBean {

    private int patId;

    private String firstName;

    private String lastName;

    public Sex sex;

    public enum Sex {
        F,
        M
    }

    private String dob;

    private String address;

    private String phone;
}
