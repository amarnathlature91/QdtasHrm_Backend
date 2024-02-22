package com.qdtas.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int userId;
    private String email;
    private String username;
    @JsonIgnore
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private int PhoneNumber;
    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department dept;
    @Column(length = 150)
    private String address;
    @Column(length = 256)
    private String profilePhoto;
    @Column(length = 32, nullable = false)
    private String role;
    private Boolean emailVerified;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date joinDate;
}
