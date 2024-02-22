package com.qdtas.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
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

    private String userName;

    private String email;

    @JsonIgnore
    private String password;

    private String firstName;

    private String middleName;

    private String lastName;

    private String gender;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department dept;

    @Column(length = 32, nullable = false)
    private String role;

    private BigInteger phoneNumber;

    @Column(length = 150)
    private String address;

    @Column(length = 100)
    private String designation;

    private Boolean emailVerified;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date joinDate;
}
