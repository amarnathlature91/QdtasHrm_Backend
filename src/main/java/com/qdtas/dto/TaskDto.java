package com.qdtas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qdtas.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private Long id;

    @NotNull(message="name should contains min 3 chars")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only alphabets")
    private String name;

    @NotNull(message = "description should not be empty")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-mm-dd")
    private String dueDate;

    @NotNull(message="assignee cannot be empty")
    @JoinColumn(name="assignee_id")
    private long assignee;

    private long empId;
}
