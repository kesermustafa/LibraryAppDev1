package com.lib.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MostBorrowersResponse {


    private Long userId;
    private String userName;
    private String userLastName;
    private Long count;


}
