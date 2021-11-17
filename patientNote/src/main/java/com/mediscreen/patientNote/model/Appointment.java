package com.mediscreen.patientNote.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "mediscreen")
public class Appointment {

    @GeneratedValue
    private int appointmentId;

    private int patId;

    private Date date;

    private String note;

    private String doctorName;
}
