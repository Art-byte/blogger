package com.artbyte.blog.record;

import java.util.List;

public record UserProfile(
        String username,
        String name,
        String lastName,
        List<String> socialMedia,
        String aboutMe
) {
}
