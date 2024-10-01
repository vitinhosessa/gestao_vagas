package dev.vitinho.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
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
@Entity(name = "company")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank()
  @Pattern(regexp = "\\S+", message = "O campo username não deve conter espaço!")
  private String username;

  @NotBlank(message = "Esse campo é necessario")
  @Length(min = 10, max = 100, message = "A senha deve conter entre 10 e 100 caracteres!")
  private String password;

  @NotBlank(message = "Esse campo é necessario")
  @Email(message = "O campo email deve conter um email valido!")
  private String email;

  @NotBlank(message = "Esse campo é necessario")
  private String website;

  @NotBlank(message = "Esse campo é necessario")
  private String name;

  @NotBlank(message = "Esse campo é necessario")
  private String description;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
