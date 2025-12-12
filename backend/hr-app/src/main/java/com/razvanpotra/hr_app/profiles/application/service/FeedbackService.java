package com.razvanpotra.hr_app.profiles.application.service;

import com.razvanpotra.hr_app.exceptions.UserNotFoundException;
import com.razvanpotra.hr_app.profiles.application.ports.FeedbackPort;
import com.razvanpotra.hr_app.profiles.application.ports.ProfilesPort;
import com.razvanpotra.hr_app.profiles.infra.mapper.UserMapper;
import com.razvanpotra.hr_app.profiles.infra.persistence.entities.FeedbackEntity;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackService {

  private final FeedbackPort feedbackPort;
  private final ProfilesPort profilesPort;

  @Transactional
  public FeedbackEntity createFeedback(String author, String text, String profileUsername) {
    var user = profilesPort.getProfileByUserName(profileUsername);
    var currentDate = LocalDateTime.now();
    var feedback = FeedbackEntity.builder()
        .id(UUID.randomUUID().toString())
        .author(author)
        .text(text)
        .date(currentDate)
        .user(user)
        .build();
    return feedbackPort.createFeedback(feedback);
  }
}
