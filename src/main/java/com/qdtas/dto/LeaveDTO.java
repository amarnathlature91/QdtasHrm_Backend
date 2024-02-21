package com.qdtas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qdtas.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.util.Date;

public class LeaveDTO {

    private long leaveId;

    @NotBlank(message = "User_Id cannot be blank")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User employee;

    @NotNull(message = "Please provide a valid birthDate")
    @Past(message = "Birthdate must be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull(message = "Please provide a valid birthDate")
    @Past(message = "Birthdate must be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    @NotBlank(message = "Reason cannot be blank")
    private String reason;

    @NotBlank(message = "Status cannot be blank")
    private String status;
}
