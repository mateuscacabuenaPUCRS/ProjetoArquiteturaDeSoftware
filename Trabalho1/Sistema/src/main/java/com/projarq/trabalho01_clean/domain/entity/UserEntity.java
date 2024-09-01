package com.projarq.trabalho01_clean.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEntity { 

    //Identificador do usuário para login 
    private String username;
    //Senha do usuário para login
    private String password;
}
