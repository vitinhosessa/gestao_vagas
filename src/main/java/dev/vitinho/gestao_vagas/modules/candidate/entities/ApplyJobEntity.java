package dev.vitinho.gestao_vagas.modules.candidate.entities;

import dev.vitinho.gestao_vagas.modules.candidate.CandidateEntity;
import dev.vitinho.gestao_vagas.modules.company.entities.JobEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "apply_jobs")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyJobEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne()
  @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
  private CandidateEntity candidateEntity;

  @ManyToOne()
  @JoinColumn(name = "job_id", insertable = false, updatable = false)
  private JobEntity jobEntity;

  @Column(name = "candidate_id")
  private UUID candidateId;

  @Column(name = "job_id")
  private UUID jobId;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
