package com.crud.blog.repositories;
import com.crud.blog.models.PostModel;
import com.crud.blog.models.UserModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.UUID;

@Repository //O Jpa abaixo já está implicito no Spring Data JPA, mas é uma boa prática usar a anotação @Repository para indicar que esta interface é um repositório.
public interface PostRepository extends JpaRepository<PostModel, UUID>{    // Esta interface estende JpaRepository, que fornece métodos CRUD para a entidade PostModel.
    Page<PostModel> findByUser(UserModel user, Pageable pageable);
    // O método findByUser permite buscar posts por usuário, utilizando paginação. O Spring Data JPA irá gerar a implementação automaticamente com base no nome do método.
}
