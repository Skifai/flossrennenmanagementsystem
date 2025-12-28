package ch.flossrennen.managementsystem.util;

import java.util.Optional;

public class CheckResult<T> {

    private final boolean success;
    private final String message;
    private final T data;

    public CheckResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> CheckResult<T> success(T data) {
        return new CheckResult<>(true, null, data);
    }

    public static <T> CheckResult<T> success(T data, String message) {
        return new CheckResult<>(true, message, data);
    }

    public static <T> CheckResult<T> failure(String message) {
        return new CheckResult<>(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Optional<T> getData() {
        return Optional.ofNullable(data);
    }
}
