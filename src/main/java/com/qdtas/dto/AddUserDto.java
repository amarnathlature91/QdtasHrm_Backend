package com.qdtas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUserDto {

    @NotBlank(message = "Username cannot be blank")
    private String userName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size   (min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Middle name cannot be blank")
    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Gender cannot be blank")
    private String gender;

    @NotNull(message = "Department ID cannot be null")
    private Long deptId;

    @NotBlank(message = "Role cannot be blank")
    private String role;

    @NotBlank(message ="Phone Number should not be blank")
    private int PhoneNumber;

    @NotNull(message = "Please provide a valid birthDate")
    @Past(message = "Birthdate must be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

}
