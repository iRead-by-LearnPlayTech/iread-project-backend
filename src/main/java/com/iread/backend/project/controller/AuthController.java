package com.iread.backend.project.controller;

import com.iread.backend.project.dto.TeacherDTO;
import com.iread.backend.project.dto.TeacherDTORequest;
import com.iread.backend.project.registration.RegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200/")
@Tag(name = "Authentication", description = "Authentication management APIs")
@AllArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<TeacherDTO> register(@Valid @RequestBody TeacherDTORequest request){
        return ResponseEntity.ok(registrationService.register(request));
    }

}
