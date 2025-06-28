package com.crud.blog.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

// A anotação @Entity indica que a classe é uma entidade JPA, ou seja, ela será mapeada para uma tabela no banco de dados.
@Entity
@Table(name= "TB_POSTS")
//Serialazable é uma interface que permite que os objetos sejam convertidos em um formato que pode ser facilmente armazenado ou transmitido, como JSON ou XML.
public class PostModel extends RepresentationModel<PostModel> implements Serializable {
    private static final long serialVersionUID = 1L;
    //serialVersionUID = 1 é utlizada para garantir a compatibilidade entre diferentes versões da classe durante a serialização e desserialização.

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO) //Gera valores automaticamente para cada item (no caso os posts) na tabela do BD
    private UUID idPost;
    private String autor;
    private String conteudo;
    private LocalDateTime dataCricao = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    public UserModel getUser() { return user; }

    public void setUser(UserModel user) { this.user = user; }

    public LocalDateTime getDataCricao() {
        return dataCricao;
    }

    public void setDataCricao(LocalDateTime dataCricao) {
        this.dataCricao = dataCricao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public UUID getIdPost() {
        return idPost;
    }

    public void setIdPost(UUID idPost) {
        this.idPost = idPost;
    }
}
// O modelo BlogModel representa a entidade de produto no banco de dados, com atributos como idProduct, name e value.
