package com.pragma.plazoleta.user.infrastructure.out.encoder.adapter;

import com.pragma.plazoleta.user.domain.spi.IPasswordEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class PasswordEncoderAdapter implements IPasswordEncoderPort {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String encode(String text) {
        return passwordEncoder.encode(text);
    }
}
