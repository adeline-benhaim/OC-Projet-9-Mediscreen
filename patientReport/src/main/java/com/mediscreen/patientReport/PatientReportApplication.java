package com.mediscreen.patientReport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mediscreen.patientReport")
public class PatientReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientReportApplication.class, args);
    }

}
