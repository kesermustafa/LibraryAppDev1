package com.lib.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {


    @NotNull(message = "Category cannot be null")
    @Size(min = 4,max = 80,message = "Category name '${validateValue}' should be between {min} and {max}")
    private String name;
    private boolean builtIn=false;

    private int sequence;



}
