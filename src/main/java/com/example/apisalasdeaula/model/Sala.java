package com.example.apisalasdeaula.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "salas")
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Sala de aula da UFSM")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador da sala", example = "1")
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(nullable = false, length = 50)
    @Schema(description = "Nome da sala", example = "Sala de Informática")
    private String nome;

    @NotBlank
    @Size(max = 10)
    @Column(name = "codigo_sala", nullable = false, unique = true, length = 10)
    @Schema(description = "Código único da sala", example = "SALA101")
    private String codigo;

    @NotNull
    @Min(10)
    @Max(200)
    @Column(name = "capacidade_alunos", nullable = false)
    @Schema(description = "Capacidade de alunos", example = "40")
    private Integer capacidadeAlunos;

    @Min(0)
    @Column(name = "quantidade_computadores")
    @Schema(description = "Quantidade de computadores", example = "20")
    private Integer quantidadeComputadores;

    @NotNull
    @Min(1960)
    @Max(2026)
    @Column(name = "ano_construcao", nullable = false)
    @Schema(description = "Ano de construção da sala", example = "2010")
    private Integer anoConstrucao;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false)
    @Schema(description = "Área da sala em metros quadrados", example = "55.50")
    private BigDecimal area;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Situação da sala", example = "DISPONIVEL")
    private Situacao situacao;
}
