package dev.vitinho.gestao_vagas.modules.candidate.useCases;

import dev.vitinho.gestao_vagas.exceptions.JobNotFoundException;
import dev.vitinho.gestao_vagas.exceptions.UserNotFoundException;
import dev.vitinho.gestao_vagas.modules.candidate.CandidateEntity;
import dev.vitinho.gestao_vagas.modules.candidate.CandidateRepository;
import dev.vitinho.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import dev.vitinho.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import dev.vitinho.gestao_vagas.modules.company.entities.JobEntity;
import dev.vitinho.gestao_vagas.modules.company.repositories.JobRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private JobRepository jobRepository;

  @Mock
  private ApplyJobRepository applyJobRepository;


  @Test
  @DisplayName("Should not be able to apply job with candidate not found")
  public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
    try {
      applyJobCandidateUseCase.execute(null, null);
    } catch(Exception e) {
      assertThat(e).isInstanceOf(UserNotFoundException.class);
    }
  }


  @Test
  @DisplayName("Should not be able to apply job with job not found")
  public void shouldNotBeAbleToApplyJobWithJobNotFound() {
    var idCandidate = UUID.randomUUID();

    var candidate = new CandidateEntity();
    candidate.setId(idCandidate);

    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

    try {
      applyJobCandidateUseCase.execute(idCandidate, null);
    } catch(Exception e) {
      assertThat(e).isInstanceOf(JobNotFoundException.class);
    }
  }


  @Test
  @DisplayName("Should be able to apply job")
  public void shouldBeAbleToApplyJob() {
    var idCandidate = UUID.randomUUID();
    var idJob = UUID.randomUUID();
    var idApplyJob = UUID.randomUUID();

    var candidate = new CandidateEntity();
    candidate.setId(idCandidate);

    var job = new JobEntity();
    job.setId(idJob);

    var applyJob = ApplyJobEntity.builder()
      .candidateId(idCandidate)
      .jobId(idJob)
      .build();

    var applyJobCreated = ApplyJobEntity.builder()
      .id(idApplyJob)
      .candidateId(idCandidate)
      .jobId(idJob)
      .build();

    when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));
    when(jobRepository.findById(idJob)).thenReturn(Optional.of(job));

    when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

    var result = applyJobCandidateUseCase.execute(idCandidate, idJob);
    assertThat(result).hasFieldOrProperty("id");
    assertNotNull(result.getId());
  }
}
