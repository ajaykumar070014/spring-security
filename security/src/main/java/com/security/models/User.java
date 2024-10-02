package com.security.models;

import com.security.util.CustomIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private boolean enabled;
    private String refreshToken;
    private Date refreshTokenExpiry;

    public User(){
        this.id =  CustomIdGenerator.generateId(this.getClass().getSimpleName());
    }
    public User(String id) {
        this.id = id;
    }
}


