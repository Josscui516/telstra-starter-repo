package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.dto.ActuatorRequest;
import au.com.telstra.simcardactivator.dto.ActuatorResponse;
import au.com.telstra.simcardactivator.model.ActivationRecord;
import au.com.telstra.simcardactivator.repository.ActivationRecordRepository;
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
    private final ActivationRecordRepository activationRecordRepository;

    public ActivationService(RestTemplate restTemplate, ActivationRecordRepository activationRecordRepository) {
        this.restTemplate = restTemplate;
        this.activationRecordRepository = activationRecordRepository;
    }

    public boolean activate(String iccid, String customerEmail) {
        boolean success = false;
        try {
            ActuatorResponse response = restTemplate.postForObject(
                ACTUATOR_URL,
                new ActuatorRequest(iccid),
                ActuatorResponse.class
            );
            success = response != null && response.isSuccess();
            logger.info("Activation result for iccid {}: {}", iccid, success);
        } catch (RestClientException ex) {
            logger.warn("Activation request failed for iccid {}: {}", iccid, ex.getMessage());
        }
        activationRecordRepository.save(new ActivationRecord(iccid, customerEmail, success));
        return success;
    }
}
