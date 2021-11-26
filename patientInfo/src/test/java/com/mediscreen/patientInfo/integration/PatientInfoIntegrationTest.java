package com.mediscreen.patientInfo.integration;

import com.mediscreen.patientInfo.model.Patient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.mediscreen.patientInfo.config.DataSourceTest.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientInfoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET request (/patientInfo) must return a list of all patients")
    public void getAllPatientsTest() throws Exception {

        mockMvc.perform(get("/patientInfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Edward")));
    }

    @Test
    @DisplayName("GET request (/patientInfo/search/{firstName}/{lastName}) must return a list of patients found by firstName and lastName")
    public void getPatientListByFirstNameAndLastNameTest() throws Exception {

        mockMvc.perform(get("/patientInfo/search/Lucas/Ferguson"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName", is("Ferguson")))
                .andExpect(jsonPath("$[0].firstName", is("Lucas")))
                .andExpect(jsonPath("$[0].dob", is("1968-06-22")));
    }

    @Test
    @DisplayName("GET request (/patientInfo/search/{firstName}/{lastName}) with unknown patient must return an HTTP 404 response")
    public void getPatientListByFirstNameAndLastNameWithUnknownPatientTest() throws Exception {

        mockMvc.perform(get("/patientInfo/search/Marc/Ferguson"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET request (/patientInfo/search/{id}) must return a patients found by id")
    public void getPatientByIdTest() throws Exception {

        mockMvc.perform(get("/patientInfo/search/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("patId", is(1)))
                .andExpect(jsonPath("lastName", is("Ferguson")))
                .andExpect(jsonPath("firstName", is("Lucas")))
                .andExpect(jsonPath("dob", is("1968-06-22")));
    }

    @Test
    @DisplayName("GET request (/patientInfo/search/{id}) with unknown patient id must return an HTTP 404 response")
    public void getPatientByUnknownIdTest() throws Exception {

        mockMvc.perform(get("/patientInfo/search/10000"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT request (/patientInfo/update) must update patient info")
    public void putPatientInfoTest() throws Exception {

        //GIVEN
        Patient patient = Patient.builder().patId(2).firstName("updated").lastName("updated").dob("updated").sex(Patient.Sex.F).build();

        //THEN

        mockMvc.perform(MockMvcRequestBuilders
                .put("/patientInfo/update")
                .content(asJsonString(patient))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is("updated")));
    }

    @Test
    @DisplayName("PUT request (/patientInfo/update) with unknown patient must return an HTTP 404 response")
    public void putUnknownPatientInfoTest() throws Exception {

        //GIVEN
        Patient patient = Patient.builder().patId(15).firstName("updated").lastName("updated").dob("updated").sex(Patient.Sex.F).build();

        //THEN

        mockMvc.perform(MockMvcRequestBuilders
                .put("/patientInfo/update")
                .content(asJsonString(patient))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT request (/patientInfo/update) with an already existing patient must return an HTTP 400 response")
    public void putAlreadyExistingPatientInfoTest() throws Exception {

        //GIVEN
        Patient patient = Patient.builder().patId(2).firstName("Wendy").lastName("Ince").dob("1958-06-29").sex(Patient.Sex.F).build();

        //THEN

        mockMvc.perform(MockMvcRequestBuilders
                .put("/patientInfo/update")
                .content(asJsonString(patient))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT request (/patientInfo/update) with missing mandatory parameter return an HTTP 404 response")
    public void putPatientInfoWithMissingMandatoryParameterTest() throws Exception {

        //GIVEN
        Patient patient = Patient.builder().patId(2).firstName("updated").dob("1958-06-29").sex(Patient.Sex.F).build();

        //THEN

        mockMvc.perform(MockMvcRequestBuilders
                .put("/patientInfo/update")
                .content(asJsonString(patient))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST request (/patient/add) with a new patient must return an HTTP 200 response")
    public void postNewPatientTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/patient/add")
                .param("firstName", "new")
                .param("lastName", "new")
                .param("dob", "1958-06-29")
                .param("sex", "F"))
                .andExpect(jsonPath("firstName", is("new")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST request (/patient/add) with an already existing patient must return an HTTP 400 response")
    public void postAlreadyExistingPatientTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/patient/add")
                .param("firstName", "Lucas")
                .param("lastName", "Ferguson")
                .param("dob", "1968-06-22")
                .param("sex", "M"))
                .andExpect(status().isBadRequest());
    }

}
