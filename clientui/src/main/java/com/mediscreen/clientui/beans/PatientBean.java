package com.mediscreen.clientui.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
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
