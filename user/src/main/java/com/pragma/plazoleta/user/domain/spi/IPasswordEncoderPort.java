package com.pragma.plazoleta.user.domain.spi;

public interface IPasswordEncoderPort {
    String encode(String text);
}
