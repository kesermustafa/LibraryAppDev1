package com.lib.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublisherRequest {


    @NotNull(message = "name can not be null")
    @NotBlank(message = "name can not be white spase")
    @Size(min = 2, max = 25, message = "Name '${validatedValue}' must be between {min} and {max} long")
    private String name;
    private Boolean builtIn;



}
