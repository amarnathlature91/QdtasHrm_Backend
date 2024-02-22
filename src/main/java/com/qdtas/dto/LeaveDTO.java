package com.qdtas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qdtas.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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

    private long employeeId;

    @NotNull(message = "Please provide a valid birthDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull(message = "Please provide a valid birthDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    @NotBlank(message = "Reason cannot be blank")
    private String reason;
    private String status;
}
