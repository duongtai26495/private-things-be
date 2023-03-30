package com.kai.pthings.config;

import com.kai.pthings.entity.*;
import com.kai.pthings.repository.RoleRepository;
import com.kai.pthings.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(Snippets.ROLE_USER));
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(Snippets.TIME_PATTERN);

        var user = User.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName().strip())
                .email(request.getEmail().strip())
                .uname(request.getUname().strip())
                .upassword(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .active(true)
                .profile_image("")
                .joined_at(sdf.format(date))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUname(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByUname(request.getUname())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
