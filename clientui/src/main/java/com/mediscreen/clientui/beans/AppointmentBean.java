package com.mediscreen.clientui.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class AppointmentBean {

    private int appointmentId;

    private int patId;

    private Date date;

    private String note;

    private String doctorName;
}
