package com.springjwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Entity
public class ResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int _id;
    private String token;
    private LocalDateTime expiry;
    private LocalDateTime _createdAt;
    private boolean isEnabled;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    private User user;
}
