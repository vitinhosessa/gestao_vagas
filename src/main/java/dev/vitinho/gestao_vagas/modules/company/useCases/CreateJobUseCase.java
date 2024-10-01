package dev.vitinho.gestao_vagas.modules.company.useCases;

import dev.vitinho.gestao_vagas.exceptions.CompanyNotFoundException;
import dev.vitinho.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.vitinho.gestao_vagas.modules.company.entities.JobEntity;
import dev.vitinho.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private CompanyRepository companyRepository;

  public JobEntity execute(JobEntity jobEntity) {
    this.companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> {
      throw new CompanyNotFoundException();
    });

    return this.jobRepository.save(jobEntity);
  }
}
