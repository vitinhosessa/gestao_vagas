package dev.vitinho.gestao_vagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Schema(example = "Maria de Souza", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome do Candidato")
  @NotBlank(message = "Esse campo é necessario")
  private String name;

  @Schema(example = "maria", requiredMode = Schema.RequiredMode.REQUIRED, description = "Username do Candidato")
  @NotBlank()
  @Pattern(regexp = "\\S+", message = "O campo username não deve conter espaço!")
  private String username;

  @Schema(example = "maria@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "Email do Candidato")
  @NotBlank(message = "Esse campo é necessario")
  @Email(message = "O campo email deve conter um email valido!")
  private String email;

  @Schema(example = "Abc1234!", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "Senha do Candidato")
  @NotBlank(message = "Esse campo é necessario")
  @Length(min = 10, max = 100, message = "A senha deve conter entre 10 e 100 caracteres!")
  private String password;

  @Schema(example = "Desenvolvedora Java", requiredMode = Schema.RequiredMode.REQUIRED, description = "Breve Descrição do Candidato")
  @NotBlank(message = "Esse campo é necessario")
  private String description;

  private String curriculum;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
