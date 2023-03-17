package com.lib.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {

    private Long id;

    @JsonProperty("Publisher_Name")
    private String name;

    @JsonProperty("Publisher_BuiltIn")
    private Boolean builtIn;



}
