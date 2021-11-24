package com.mediscreen.patientReport.constant;

import com.mediscreen.patientReport.constants.RiskLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RiskLevelTest {

    @InjectMocks
    RiskLevel riskLevel;

    @Test
    void riskLevelTest() {

        //WHEN
        String none = RiskLevel.NONE;
        String borderline = RiskLevel.BORDERLINE;
        String inDanger = RiskLevel.IN_DANGER;
        String earlyOnset = RiskLevel.EARLY_ONSET;

        //THEN
        assertEquals("None", none);
        assertEquals("Borderline", borderline);
        assertEquals("In Danger", inDanger);
        assertEquals("Early onset", earlyOnset);
    }
}
