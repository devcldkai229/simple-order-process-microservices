package com.khaircloud.identity.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true)
    String email;

    String password;

    String lastName;

    String firstName;

    String address;

    String verifyToken;

    String refreshToken;

    boolean isActive = true;

    @ManyToMany
    Set<Role> roles;
}
