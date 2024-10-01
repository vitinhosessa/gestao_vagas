package dev.vitinho.gestao_vagas.modules.candidate.useCases;

import dev.vitinho.gestao_vagas.exceptions.JobNotFoundException;
import dev.vitinho.gestao_vagas.exceptions.UserNotFoundException;
import dev.vitinho.gestao_vagas.modules.candidate.CandidateRepository;
import dev.vitinho.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import dev.vitinho.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import dev.vitinho.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private ApplyJobRepository applyJobRepository;

  public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
    var candidate = this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
      throw new UserNotFoundException();
    });

    var job = this.jobRepository.findById(idJob).orElseThrow(() -> {
      throw new JobNotFoundException();
    });

    var applyJob = ApplyJobEntity.builder()
      .candidateId(candidate.getId())
      .jobId(job.getId())
      .build();

    return this.applyJobRepository.save(applyJob);
  }
}
