package ch.flossrennen.managementsystem.util;

public class CheckResult {

    private boolean result;
    private String message;

    public boolean isSuccess() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public CheckResult(boolean result) {
        this.result = result;
    }

    public CheckResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
