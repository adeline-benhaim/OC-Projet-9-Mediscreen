package com.mediscreen.clientui.beans;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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
