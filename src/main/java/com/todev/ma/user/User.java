package com.todev.ma.user;

import jakarta.persistence.*;
import com.todev.ma.crypto.SensitiveDataConverter;
import com.todev.ma.shared.Model;

import java.time.LocalDate;

@Entity
@Table(name = "users")
class User extends Model {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @Convert(converter = SensitiveDataConverter.class)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    @Convert(converter = SensitiveDataConverter.class)
    private String cpf;

    @Column(nullable = false, unique = true)
    @Convert(converter = SensitiveDataConverter.class)
    private String phone;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column
    private String disabilities;

    protected User() {
    }

    public User(
            String name,
            String email,
            String password,
            String cpf,
            String phone,
            Gender gender,
            Role role,
            LocalDate birthDate
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.phone = phone;
        this.gender = gender;
        this.role = role;
        this.birthDate = birthDate;
    }

    public User(
            String name,
            String email,
            String password,
            String cpf,
            String phone,
            Gender gender,
            Role role,
            LocalDate birthDate,
            String disabilities
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.phone = phone;
        this.gender = gender;
        this.role = role;
        this.birthDate = birthDate;
        this.disabilities = disabilities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getDisabilities() {
        return disabilities;
    }

    public void setDisabilities(String disabilities) {
        this.disabilities = disabilities;
    }
}
