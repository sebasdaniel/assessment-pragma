package com.pragma.plazoleta.user.infrastructure.security;

import com.pragma.plazoleta.user.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.plazoleta.user.infrastructure.out.jpa.repository.IUserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                Collections.emptyList()
        );
    }
}
