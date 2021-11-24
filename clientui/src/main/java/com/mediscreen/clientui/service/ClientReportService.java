package com.mediscreen.clientui.service;

import java.util.List;

public interface ClientReportService {

    String postAssessById(int patId);

    List<String> postAssessByName(String lastName);
}
