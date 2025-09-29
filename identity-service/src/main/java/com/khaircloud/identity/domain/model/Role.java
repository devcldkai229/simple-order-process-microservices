package com.khaircloud.identity.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    String name;

    @ManyToMany
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_name", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(name = "permission_name", referencedColumnName = "name")
    )
    Set<Permission> permissions;
}

