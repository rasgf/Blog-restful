package com.crud.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PostRecordDto(@NotBlank String titulo, @NotNull String conteudo) {
    // O record PostRecordDto é uma classe imutável que representa um post com titulo e conteúdo.
    // Ele é usado para transferir dados entre camadas da aplicação, como entre o controlador e o serviço.

}
