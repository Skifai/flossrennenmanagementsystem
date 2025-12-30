package ch.flossrennen.managementsystem.util;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CheckResultTest {

    @Test
    void success_WithData() {
        String data = "test";
        CheckResult<String> result = CheckResult.success(data);
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());
        assertEquals(data, result.getData().orElseThrow());
    }

    @Test
    void success_WithDataAndMessage() {
        String data = "test";
        String message = "success message";
        CheckResult<String> result = CheckResult.success(data, message);
        assertTrue(result.isSuccess());
        assertEquals(message, result.getMessage());
        assertEquals(data, result.getData().orElseThrow());
    }

    @Test
    void failure_WithMessage() {
        String message = "error message";
        CheckResult<String> result = CheckResult.failure(message);
        assertFalse(result.isSuccess());
        assertEquals(message, result.getMessage());
        assertTrue(result.getData().isEmpty());
    }

    @Test
    void getData_EmptyWhenNull() {
        CheckResult<String> result = CheckResult.failure("error");
        assertEquals(Optional.empty(), result.getData());
    }
}
