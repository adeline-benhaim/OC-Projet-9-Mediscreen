package com.mediscreen.patientReport.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class AppointmentBean {

    private String appointmentId;

    private int patId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime date;

    private String note;

    private String doctorName;
}
