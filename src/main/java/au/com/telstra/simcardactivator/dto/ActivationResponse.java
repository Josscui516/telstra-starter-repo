package au.com.telstra.simcardactivator.dto;

public class ActivationResponse {
    private boolean success;

    public ActivationResponse() {
    }

    public ActivationResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
