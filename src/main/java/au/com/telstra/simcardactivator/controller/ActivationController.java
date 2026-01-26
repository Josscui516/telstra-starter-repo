package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.dto.ActivationRequest;
import au.com.telstra.simcardactivator.dto.ActivationResponse;
import au.com.telstra.simcardactivator.service.ActivationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activate")
public class ActivationController {
    private final ActivationService activationService;

    public ActivationController(ActivationService activationService) {
        this.activationService = activationService;
    }

    @PostMapping
    public ResponseEntity<ActivationResponse> activate(@RequestBody ActivationRequest request) {
        boolean success = activationService.activate(request.getIccid());
        return ResponseEntity.ok(new ActivationResponse(success));
    }
}
