package com.lib.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "t_author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "Author name cannot be null")
    @Size(min = 4,max = 70,message = "Author name '${validateValue}' should be between {min} and {max}")
    private String name;


    private boolean builtIn =false;


    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Book> bookList = new ArrayList<>();


}
