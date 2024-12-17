package com.artbyte.blog.record;

public record AuthResponse(
        String jwt,
        String error
) {
}
