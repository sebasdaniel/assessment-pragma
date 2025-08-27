package com.pragma.plazoleta.user.infrastructure.out.encoder.adapter;

import com.pragma.plazoleta.user.domain.spi.IBcryptEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class BcryptEncoderAdapter implements IBcryptEncoderPort {

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String encode(String text) {
        return passwordEncoder.encode(text);
    }
}
