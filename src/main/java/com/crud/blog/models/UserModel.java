package com.crud.blog.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "TB_USERS")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    //A anotação @Column é usada para mapear os atributos da classe para as colunas da tabela no banco de dados.
    @Column(unique=true, nullable=false)
    private String username;

    @Column(nullable = false)
    private String password;


    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}