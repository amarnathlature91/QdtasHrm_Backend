package com.qdtas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUserDto {
    private String userName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
