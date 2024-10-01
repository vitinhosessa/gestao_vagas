package dev.vitinho.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobDTO {

  @Schema(example = "Vaga para pessoa desenvolvedora júnior", requiredMode = Schema.RequiredMode.REQUIRED)
  private String description;

  @Schema(example = "VR / VA, Plano de Saúde", requiredMode = Schema.RequiredMode.REQUIRED)
  private String benefits;

  @Schema(example = "JUNIOR")
  private String level;

}
