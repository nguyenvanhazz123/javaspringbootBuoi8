package com.example.buoi8.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserRole implements Serializable {
    @Column(name="user_id")
    private Integer user;

    @Column(name="role_id")
    private Integer role;

}
