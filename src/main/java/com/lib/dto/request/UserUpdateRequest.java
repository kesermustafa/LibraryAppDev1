package com.lib.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    @NotNull(message = "first name cannot be null")
    @Size(min = 2,max = 30,message = "First name '${validateValue}' should be between {min} and {max}")
    private String firstName;
    @NotNull(message = "last name cannot be null")
    @Size(min = 2,max = 30,message = "last name '${validateValue}' should be between {min} and {max}")
    private String lastName;
    @NotNull(message = "first name cannot be null")
    private Integer score=0;

    @NotNull(message = "last name cannot be null")
    @Size(min = 10,max = 100,message = "address '${validateValue}' should be between {min} and {max}")
    private String address;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", //(541) 317-8828
            message = "Please provide valid phone number")
    @NotNull(message = "phone number cannot be null")
    private String phone;


    @Column(nullable = false)
    private LocalDate birthDate;

    @NotNull(message = "password cannot be null")
    private String password;
    @Email(message = "please provide valid email")
    private String email;

    private Set<String> roles ;





}
