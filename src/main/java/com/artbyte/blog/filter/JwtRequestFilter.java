package com.artbyte.blog.filter;

import com.artbyte.blog.config.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter {

    private final JwtUtils jwtUtils;


}
