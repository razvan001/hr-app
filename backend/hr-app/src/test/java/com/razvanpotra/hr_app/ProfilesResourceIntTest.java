package com.razvanpotra.hr_app;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razvanpotra.hr_app.model.FeedbackDto;
import com.razvanpotra.hr_app.model.ProfileDto;
import com.razvanpotra.hr_app.model.ProfilesDtoResponse;
import com.razvanpotra.hr_app.profiles.infra.persistence.entities.FeedbackEntity;
import com.razvanpotra.hr_app.profiles.infra.persistence.entities.UserEntity;
import com.razvanpotra.hr_app.profiles.infra.persistence.entities.UserType;
import com.razvanpotra.hr_app.profiles.infra.persistence.repository.FeedbackRepository;
import com.razvanpotra.hr_app.profiles.infra.persistence.repository.UserRepository;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class ProfilesResourceIntTest extends BaseIntegrationTest {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private FeedbackRepository feedbackRepository;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @AfterEach
  void afterEach() {
    userRepository.deleteAll();
  }

  @Test
  void should_getPaginatedProfiles_whenValidRequest() throws Exception {

    var dummyEntity = UserEntity.builder()
        .id(UUID.randomUUID().toString())
        .type(UserType.EMPLOYEE)
        .email("test@gmail.com")
        .name("Test")
        .position("Test Position")
        .department("Test Department")
        .salary(1000.0)
        .userName("testuser")
        .build();

    userRepository.save(dummyEntity);

    var result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/profiles")
            .param("page", "0")
            .param("size", "20"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andReturn();

    var responseContent = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
    var response = objectMapper.readValue(responseContent, ProfilesDtoResponse.class);
    assertThat(response.getContent()).isNotEmpty();
    assertThat(response.getPage()).isZero();
    assertThat(response.getSize()).isEqualTo(20);
    assertThat(response.getTotalElements()).isOne();
    assertThat(response.getTotalPages()).isOne();

// assert on the first profile in the content list
    var profile = response.getContent().getFirst();
    assertThat(profile.getName()).isEqualTo("Test");
    assertThat(profile.getEmail()).isEqualTo("test@gmail.com");
    assertThat(profile.getPosition()).isEqualTo("Test Position");
    assertThat(profile.getDepartment()).isEqualTo("Test Department");
    assertThat(profile.getSalary()).isEqualTo(1000.0);
    assertThat(profile.getUserName()).isEqualTo("testuser");
    assertThat(profile.getType()).isEqualTo(UserType.EMPLOYEE.name());
  }

  @Test
  void should_updateProfile_whenValidRequest() throws Exception {
    // Arrange: create and save a dummy profile
    var dummyEntity = UserEntity.builder()
        .id(UUID.randomUUID().toString())
        .type(UserType.EMPLOYEE)
        .email("test@gmail.com")
        .name("Test")
        .position("Test Position")
        .department("Test Department")
        .salary(1000.0)
        .userName("testuser")
        .build();
    userRepository.save(dummyEntity);

    // Prepare updated profile DTO
    var updatedProfile = new ProfileDto();
    updatedProfile.setId(dummyEntity.getId());
    updatedProfile.setName("Updated Name");
    updatedProfile.setEmail("updated@gmail.com");
    updatedProfile.setPosition("Updated Position");
    updatedProfile.setDepartment("Updated Department");
    updatedProfile.setSalary(2000.0);
    updatedProfile.setUserName("testuser");

    var requestBody = objectMapper.writeValueAsString(updatedProfile);

    // Act: perform PUT request
    var result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/profiles/{userName}", "testuser")
            .contentType("application/json")
            .content(requestBody))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andReturn();

    // Assert: verify updated fields
    var responseContent = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
    var response = objectMapper.readValue(responseContent, ProfileDto.class);
    assertThat(response.getName()).isEqualTo("Updated Name");
    assertThat(response.getEmail()).isEqualTo("updated@gmail.com");
    assertThat(response.getPosition()).isEqualTo("Updated Position");
    assertThat(response.getDepartment()).isEqualTo("Updated Department");
    assertThat(response.getSalary()).isEqualTo(2000.0);
    assertThat(response.getUserName()).isEqualTo("testuser");
  }

  @Test
  void should_getProfile_whenValidUserName() throws Exception {
    // Arrange: create and save a dummy profile
    var dummyEntity = UserEntity.builder()
        .id(UUID.randomUUID().toString())
        .type(UserType.EMPLOYEE)
        .email("profile@gmail.com")
        .name("Profile User")
        .position("Profile Position")
        .department("Profile Department")
        .salary(1500.0)
        .userName("profileuser")
        .build();
    userRepository.save(dummyEntity);

    var currentDate = LocalDateTime.now();
    var yesterday = currentDate.minusDays(1);

    var feedback1 = FeedbackEntity.builder()
        .id(UUID.randomUUID().toString())
        .author("Alice")
        .text("Great work!")
        .date(currentDate)
        .user(dummyEntity)
        .build();

    var feedback2 = FeedbackEntity.builder()
        .id(UUID.randomUUID().toString())
        .author("Bob")
        .text("Needs improvement.")
        .date(yesterday)
        .user(dummyEntity)
        .build();

    feedbackRepository.save(feedback1);
    feedbackRepository.save(feedback2);

    // Act: perform GET request
    var result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/profiles/{userName}", "profileuser"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andReturn();

    // Assert: verify returned profile
    var responseContent = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
    var profileDto = objectMapper.readValue(responseContent, ProfileDto.class);
    assertThat(profileDto.getName()).isEqualTo("Profile User");
    assertThat(profileDto.getEmail()).isEqualTo("profile@gmail.com");
    assertThat(profileDto.getPosition()).isEqualTo("Profile Position");
    assertThat(profileDto.getDepartment()).isEqualTo("Profile Department");
    assertThat(profileDto.getSalary()).isEqualTo(1500.0);
    assertThat(profileDto.getUserName()).isEqualTo("profileuser");
    assertThat(profileDto.getType()).isEqualTo(UserType.EMPLOYEE.name());

    // Assert feedbacks and their ordering
    assertThat(profileDto.getFeedbacks()).hasSize(2);
    assertThat(profileDto.getFeedbacks().get(0).getAuthor()).isEqualTo("Alice");
    assertThat(profileDto.getFeedbacks().get(0).getText()).isEqualTo("Great work!");
    assertThat(profileDto.getFeedbacks().get(1).getAuthor()).isEqualTo("Bob");
    assertThat(profileDto.getFeedbacks().get(1).getText()).isEqualTo("Needs improvement.");
    assertThat(profileDto.getFeedbacks()).extracting(FeedbackDto::getId)
        .containsExactly(feedback1.getId(), feedback2.getId());
  }
}
