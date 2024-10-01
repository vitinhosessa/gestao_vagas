package dev.vitinho.gestao_vagas.modules.candidate.controllers;

import dev.vitinho.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import dev.vitinho.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import dev.vitinho.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import dev.vitinho.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import dev.vitinho.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import dev.vitinho.gestao_vagas.modules.candidate.CandidateEntity;
import dev.vitinho.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

  @Autowired
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @PostMapping("/")
  @Operation(summary = "Cadastro de Candidato", description = "Essa rota é responsável por cadastrar um candidato.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(
        schema = @Schema(implementation = CandidateEntity.class)
      )
    }),
    @ApiResponse(responseCode = "400", description = "Usuario já existente!")
  })
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      var result = this.createCandidateUseCase.execute(candidateEntity);

      return ResponseEntity.ok().body(result);
    } catch(Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Perfil do Candidato", description = "Essa rota é responsável por buscar as informações do perfil do candidato.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(
        schema = @Schema(implementation = ProfileCandidateResponseDTO.class)
      )
    }),
    @ApiResponse(responseCode = "400", description = "Usuario não encontrado!")
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> get(HttpServletRequest request) {
    var candidateId = request.getAttribute("candidate_id");
    try {
      var candidateProfile = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));

      return ResponseEntity.ok().body(candidateProfile);
    } catch(Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/job")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Listagem de vagas disponível para o candidato", description = "Essa rota é responsável por listar todas as vagas disponíveis, baseada no filtro.")
  @ApiResponses(
    @ApiResponse(responseCode = "200", content = {
      @Content(
        array = @ArraySchema(schema = @Schema(
          implementation = JobEntity.class
        ))
      )
    })
  )
  @SecurityRequirement(name = "jwt_auth")
  public List<JobEntity> findJobByFilter(@RequestParam String filter) {
    return this.listAllJobsByFilterUseCase.execute(filter);
  }

  @PostMapping("/job/apply")
  @PreAuthorize("hasRole('CANDIDATE')")
  @SecurityRequirement(name = "jwt_auth")
  @Operation(summary = "Inscrição do candidato para uma vaga", description = "Essa rota é responsável por realizar a inscrição do candidato em uma vaga.")
  public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob) {
    var idCandidate = request.getAttribute("candidate_id");

    try {
      var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);

      return ResponseEntity.ok().body(result);
    } catch(Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
