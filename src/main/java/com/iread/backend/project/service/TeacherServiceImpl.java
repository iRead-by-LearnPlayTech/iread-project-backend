package com.iread.backend.project.service;

import com.iread.backend.project.config.jwt.JwtService;
import com.iread.backend.project.dto.AuthDTO;
import com.iread.backend.project.dto.AuthenticationDTORequest;
import com.iread.backend.project.entity.Role;
import com.iread.backend.project.entity.Teacher;
import com.iread.backend.project.exception.EmailExistsException;
import com.iread.backend.project.registration.token.ConfirmationToken;
import com.iread.backend.project.registration.token.ConfirmationTokenService;
import com.iread.backend.project.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public String singUpUser(Teacher user) {
        if (teacherRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new EmailExistsException("Email ingresado ya existe");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        teacherRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    @Override
    public AuthDTO authenticate(AuthenticationDTORequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = teacherRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException("Teacher not founded"));

        Long teacherID = user.getId();
        var extraClaims = new HashMap<String, Object>();

        var jwtToken = jwtService.generateToken(teacherID.toString(), extraClaims, user);

        return AuthDTO.builder()
                .token(jwtToken).build();
    }

    @Override
    public int enableUser(String email) {
        return teacherRepository.enableUser(email);
    }

}