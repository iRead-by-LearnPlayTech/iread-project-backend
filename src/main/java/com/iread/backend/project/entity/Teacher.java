package com.iread.backend.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iread.backend.project.token.Token;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teacher")
public class Teacher implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;

    @Column(name = "teacher_name", nullable = false)
    @Size(max = 25)
    private String name;

    @Column(name = "teacher_surname", nullable = false)
    @Size(max = 25)
    private String surname;

    @Column(name = "teacher_email", nullable = false, unique = true)
    @Size(max = 40)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "enabled")
    private Boolean enabled = false;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    private List<Story> stories;

    @Column(name = "recovery_token")
    private String recoveryToken;

    @Column(name = "recovery_token_expiry")
    private LocalDateTime recoveryTokenExpiry;

    @OneToMany(mappedBy = "teacher")
    private List<Token> tokens;

    public Teacher(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}