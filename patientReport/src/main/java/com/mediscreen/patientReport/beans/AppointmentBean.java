package com.mediscreen.patientReport.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class AppointmentBean {

    private String appointmentId;

    private int patId;

    private String note;

    private String doctorName;
}
