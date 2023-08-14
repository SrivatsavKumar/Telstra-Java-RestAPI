package au.com.telstra.simcardactivator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SimActivationController {
    private final RestTemplate restTemplate;

    public SimActivationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/activate")
    public String activateSim(@RequestBody SimActivationRequest request) {
        ActuatorRequest actuatorRequest = new ActuatorRequest(request.getIccid());

        ActuatorResponse actuatorResponse = restTemplate.postForObject(
                "http://localhost:8444/actuate",
                actuatorRequest,
                ActuatorResponse.class
        );

        if (actuatorResponse != null && actuatorResponse.isSuccess()) {
            return "SIM Card Activation is Successful";
        } else {
            return "SIM Card Activation failed";
        }
    }
}
