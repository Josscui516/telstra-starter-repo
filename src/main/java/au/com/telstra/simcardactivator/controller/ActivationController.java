package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.dto.ActivationRequest;
import au.com.telstra.simcardactivator.dto.ActivationResponse;
import au.com.telstra.simcardactivator.dto.ActivationStatusResponse;
import au.com.telstra.simcardactivator.model.ActivationRecord;
import au.com.telstra.simcardactivator.repository.ActivationRecordRepository;
import au.com.telstra.simcardactivator.service.ActivationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activate")
public class ActivationController {
    private final ActivationService activationService;
    private final ActivationRecordRepository activationRecordRepository;

    public ActivationController(ActivationService activationService, ActivationRecordRepository activationRecordRepository) {
        this.activationService = activationService;
        this.activationRecordRepository = activationRecordRepository;
    }

    @PostMapping
    public ResponseEntity<ActivationResponse> activate(@RequestBody ActivationRequest request) {
        boolean success = activationService.activate(request.getIccid(), request.getCustomerEmail());
        return ResponseEntity.ok(new ActivationResponse(success));
    }

    @GetMapping
    public ResponseEntity<ActivationStatusResponse> getActivation(@RequestParam("simCardId") long simCardId) {
        return activationRecordRepository.findById(simCardId)
            .map(record -> ResponseEntity.ok(toStatusResponse(record)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ActivationStatusResponse toStatusResponse(ActivationRecord record) {
        return new ActivationStatusResponse(record.getIccid(), record.getCustomerEmail(), record.isActive());
    }
}
