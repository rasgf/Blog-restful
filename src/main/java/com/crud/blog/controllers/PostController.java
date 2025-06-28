package com.crud.blog.controllers;

import com.crud.blog.dto.PostRecordDto;
import com.crud.blog.models.PostModel;
import com.crud.blog.repositories.PostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PagedResourcesAssembler<PostModel> pagedResourcesAssembler;

    @PostMapping("/posts")
    public ResponseEntity<PostModel> savePost(@RequestBody @Valid PostRecordDto postRecordDto) {
        var postModel = new PostModel();
        BeanUtils.copyProperties(postRecordDto, postModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(postRepository.save(postModel));
    }

    @GetMapping("/posts")
    public ResponseEntity<PagedModel<EntityModel<PostModel>>> getMyPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostModel> postsPage = postRepository.findAll(pageable);

        PagedModel<EntityModel<PostModel>> postsPagedModel = pagedResourcesAssembler.toModel(postsPage, post ->
                EntityModel.of(post,
                        linkTo(methodOn(PostController.class).getOnePost(post.getIdPost())).withSelfRel(),
                        linkTo(methodOn(PostController.class).getMyPosts(page, size)).withRel("posts")
                )
        );

        return ResponseEntity.status(HttpStatus.OK).body(postsPagedModel);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<EntityModel<PostModel>> getOnePost(@PathVariable(value = "id") UUID id) {
        Optional<PostModel> postOpt = postRepository.findById(id);
        if (postOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        PostModel post = postOpt.get();
        EntityModel<PostModel> postResource = EntityModel.of(post,
                linkTo(methodOn(PostController.class).getOnePost(id)).withSelfRel(),
                linkTo(methodOn(PostController.class).getMyPosts(0, 10)).withRel("posts")
        );
        return ResponseEntity.status(HttpStatus.OK).body(postResource);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable(value = "id") UUID id, @RequestBody @Valid PostRecordDto postRecordDto) {
        Optional<PostModel> postOpt = postRepository.findById(id);
        if (postOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post não encontrado");
        }
        var postModel = postOpt.get();
        BeanUtils.copyProperties(postRecordDto, postModel);
        return ResponseEntity.status(HttpStatus.OK).body(postRepository.save(postModel));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable(value = "id") UUID id) {
        Optional<PostModel> postOpt = postRepository.findById(id);
        if (postOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post não encontrado");
        }
        postRepository.delete(postOpt.get());
        return ResponseEntity.status(HttpStatus.OK).body("Post deletado com sucesso");
    }
}
