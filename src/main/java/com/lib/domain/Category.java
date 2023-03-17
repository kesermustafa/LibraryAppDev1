package com.lib.domain;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "t_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Category cannot be null")
    @Size(min = 4,max = 80,message = "Category name '${validateValue}' should be between {min} and {max}")
    private String name;
    private boolean builtIn=false;

    @NotNull(message = "Category name cannot be null")
    private int sequence;


}
