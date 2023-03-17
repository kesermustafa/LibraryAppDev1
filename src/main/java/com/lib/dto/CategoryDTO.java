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
public class CategoryDTO {

    private Long id;
    @JsonProperty("Category_Name")
    private String name;
    @JsonProperty("Category_BuiltIn")
    private boolean builtIn=false;
    @JsonProperty("Category_Sequence")
    private int sequence;


}
