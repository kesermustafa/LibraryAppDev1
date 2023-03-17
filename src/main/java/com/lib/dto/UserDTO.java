package com.lib.dto;

import com.lib.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;

    private Integer score;

    private String address;
    private String phone;

    private LocalDate birthDate;

    private String email;
    private String password;

    private LocalDateTime createDate = LocalDateTime.now();

    private String resetPasswordCode;

    private Boolean builtIn;

    private Set<String> roles;

    public void setRoles(Set<Role> roles){
        Set<String> roleStr = new HashSet<>();
        roles.forEach(r->{
            roleStr.add(r.getRoleType().getName()); // Customer , Administrator
        });

        this.roles = roleStr;
    }


}
