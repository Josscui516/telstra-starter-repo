package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.dto.ActuatorRequest;
import au.com.telstra.simcardactivator.dto.ActuatorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ActivationService {
    private static final Logger logger = LoggerFactory.getLogger(ActivationService.class);
    private static final String ACTUATOR_URL = "http://localhost:8444/actuate";

    private final RestTemplate restTemplate;

    public ActivationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean activate(String iccid) {
        try {
            ActuatorResponse response = restTemplate.postForObject(
                ACTUATOR_URL,
                new ActuatorRequest(iccid),
                ActuatorResponse.class
            );
            boolean success = response != null && response.isSuccess();
            logger.info("Activation result for iccid {}: {}", iccid, success);
            return success;
        } catch (RestClientException ex) {
            logger.warn("Activation request failed for iccid {}: {}", iccid, ex.getMessage());
            return false;
        }
    }
}
