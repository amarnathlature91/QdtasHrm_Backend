package com.qdtas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qdtas.entity.User;
import com.qdtas.utility.NonZero;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDTO {

    private long leaveId;

    @NonZero
    private long employeeId;

    @NotNull(message = "Please provide a valid birthDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull(message = "Please provide a valid birthDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphabets are allowed")
    @NotBlank(message = "Reason cannot be blank")
    private String reason;
    private String status;
}
