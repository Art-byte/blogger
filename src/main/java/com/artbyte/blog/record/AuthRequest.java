package com.artbyte.blog.record;

public record AuthRequest(
        String username,
        String password
) {
}
