package org.com.programming.animal.entity.DTOs;

import java.time.Instant;

public record AuthResponse(String token, Instant timestamp) {
}
