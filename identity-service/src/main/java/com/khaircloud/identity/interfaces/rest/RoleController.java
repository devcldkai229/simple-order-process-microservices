package com.khaircloud.identity.interfaces.rest;

import com.khaircloud.identity.application.dto.ApiResponse;
import com.khaircloud.identity.application.dto.request.CreateRoleRequest;
import com.khaircloud.identity.domain.model.Permission;
import com.khaircloud.identity.domain.model.Role;
import com.khaircloud.identity.infrastructure.repository.PermissionRepository;
import com.khaircloud.identity.infrastructure.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<?>> createRole(@RequestBody CreateRoleRequest request) {
        List<Permission> permissionList = request.getPermissions().stream()
                .map(x -> Permission.builder().name(x).build())
                .toList();

        permissionRepository.saveAll(permissionList);
        Role role = Role.builder()
                .name(request.getName())
                .permissions(new HashSet<>(permissionList))
                .build();

        roleRepository.save(role);

        return ResponseEntity.ok(ApiResponse.builder().data("Add role success").build());
    }


}
