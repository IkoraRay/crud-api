package com.onboarding.crud.api.entities;

import com.onboarding.crud.api.dto.UserInsertDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String email;
    private String name;
    private String password;
    private String username;

    public User (String uuid,UserInsertDto user) {
        id = uuid;
        email = user.email();
        name = user.name();
        password = user.password();
        username = user.username();
    }
}
