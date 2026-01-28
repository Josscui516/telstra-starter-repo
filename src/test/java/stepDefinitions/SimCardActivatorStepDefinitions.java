package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import au.com.telstra.simcardactivator.dto.ActivationRequest;
import au.com.telstra.simcardactivator.dto.ActivationResponse;
import au.com.telstra.simcardactivator.dto.ActivationStatusResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    private ActivationRequest activationRequest;
    private ActivationResponse activationResponse;

    @Given("a customer submits an activation request with iccid {string} and email {string}")
    public void aCustomerSubmitsAnActivationRequestWithIccidAndEmail(String iccid, String email) {
        ActivationRequest request = new ActivationRequest();
        request.setIccid(iccid);
        request.setCustomerEmail(email);
        this.activationRequest = request;
    }

    @When("the activation request is sent")
    public void theActivationRequestIsSent() {
        activationResponse = restTemplate.postForObject(
            "http://localhost:8080/activate",
            activationRequest,
            ActivationResponse.class
        );
        assertNotNull(activationResponse);
    }

    @Then("the activation record with id {long} should be active")
    public void theActivationRecordWithIdShouldBeActive(long id) {
        ActivationStatusResponse status = restTemplate.getForObject(
            "http://localhost:8080/activate?simCardId=" + id,
            ActivationStatusResponse.class
        );
        assertNotNull(status);
        assertEquals(true, status.isActive());
    }

    @Then("the activation record with id {long} should be inactive")
    public void theActivationRecordWithIdShouldBeInactive(long id) {
        ActivationStatusResponse status = restTemplate.getForObject(
            "http://localhost:8080/activate?simCardId=" + id,
            ActivationStatusResponse.class
        );
        assertNotNull(status);
        assertEquals(false, status.isActive());
    }
}
