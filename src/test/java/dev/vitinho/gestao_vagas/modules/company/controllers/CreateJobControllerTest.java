package dev.vitinho.gestao_vagas.modules.company.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vitinho.gestao_vagas.exceptions.CompanyNotFoundException;
import dev.vitinho.gestao_vagas.modules.company.dto.CreateJobDTO;
import dev.vitinho.gestao_vagas.modules.company.entities.CompanyEntity;
import dev.vitinho.gestao_vagas.modules.company.repositories.CompanyRepository;
import dev.vitinho.gestao_vagas.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private CompanyRepository companyRepository;

  @Before
  public void setup() {
    mvc = MockMvcBuilders
      .webAppContextSetup(context)
      .apply(SecurityMockMvcConfigurers.springSecurity())
      .build();
  }

  @Test
  public void shouldBeAbleToCreateNewJob() throws Exception {
    var company = CompanyEntity.builder()
      .description("COMPANY_DESCRIPTION")
      .email("email@company.com")
      .password("1234567890")
      .username("COMPANY_USERNAME")
      .name("COMPANY_NAME")
      .website("https://company.site")
      .build();

    company = companyRepository.saveAndFlush(company);

    var createdJobDTO = CreateJobDTO.builder()
      .benefits("BENEFITS_TEST")
      .description("DESCRIPTION_TEST")
      .level("LEVEL_TEST")
      .build();

    mvc.perform(MockMvcRequestBuilders.post("/company/job/")
      .contentType(MediaType.APPLICATION_JSON)
      .content(TestUtils.objectToJSON(createdJobDTO))
      .header("Authorization", TestUtils.generateToken(company.getId(), "JAVAGAS_123$"))
    ).andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void shouldNotBeAbleToCreateNewJobIfCompanyNotFound() throws Exception {
    var createdJobDTO = CreateJobDTO.builder()
      .benefits("BENEFITS_TEST")
      .description("DESCRIPTION_TEST")
      .level("LEVEL_TEST")
      .build();

    mvc.perform(MockMvcRequestBuilders.post("/company/job/")
      .contentType(MediaType.APPLICATION_JSON)
      .content(TestUtils.objectToJSON(createdJobDTO))
      .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "JAVAGAS_123$"))
    ).andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
