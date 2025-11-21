package com.example.springaichatmodel.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDetail {
    @Id
    private String id;

    private String name;

    private String email;

    private int age;
}
