package com.qdtas.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Only alphabets are allowed")
    private String projectName;

    private  String description;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabets are allowed")
    private String status;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabets are allowed")
    private String type;

    private String client;

}
