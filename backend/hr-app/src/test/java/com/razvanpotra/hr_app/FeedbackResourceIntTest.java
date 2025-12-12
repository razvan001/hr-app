package com.razvanpotra.hr_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razvanpotra.hr_app.model.FeedbackCreateDto;
import com.razvanpotra.hr_app.model.FeedbackDto;
import com.razvanpotra.hr_app.profiles.infra.persistence.entities.UserEntity;
import com.razvanpotra.hr_app.profiles.infra.persistence.entities.UserType;
import com.razvanpotra.hr_app.profiles.infra.persistence.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FeedbackResourceIntTest extends BaseIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private String userName;

  @BeforeEach
  void setUp() {
    var user = UserEntity.builder()
        .id(UUID.randomUUID().toString())
        .type(UserType.EMPLOYEE)
        .email("feedbackuser@gmail.com")
        .name("Feedback User")
        .position("Dev")
        .department("IT")
        .salary(2000.0)
        .userName("feedbackuser")
        .build();
    userRepository.save(user);
    userName = user.getUserName();
  }

  @AfterEach
  void afterEach() {
    userRepository.deleteAll();
  }

  @Test
  void should_createFeedback_when_validRequest() throws Exception {
    var dto = new FeedbackCreateDto()
        .author("Jane Doe")
        .text("Great work!")
        .profileUsername(userName);

    var result = mockMvc.perform(post("/api/v1/feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    var responseContent = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
    var feedback = objectMapper.readValue(responseContent, FeedbackDto.class);

    assertThat(feedback.getAuthor()).isEqualTo("Jane Doe");
    assertThat(feedback.getText()).isEqualTo("Great work!");
  }

  @Test
  void should_return404_when_profileNotFound() throws Exception {
    var dto = new FeedbackCreateDto()
        .author("John Doe")
        .text("Missing profile")
        .profileUsername("non-existent-username");

    mockMvc.perform(post("/api/v1/feedbacks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isNotFound());
  }
}

