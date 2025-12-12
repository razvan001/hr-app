package com.razvanpotra.hr_app.profiles.infra.persistence;

import com.razvanpotra.hr_app.profiles.application.ports.FeedbackPort;
import com.razvanpotra.hr_app.profiles.infra.persistence.entities.FeedbackEntity;
import com.razvanpotra.hr_app.profiles.infra.persistence.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class FeedbackAdapter implements FeedbackPort {

  private final FeedbackRepository feedbackRepository;

  @Override
  public FeedbackEntity createFeedback(FeedbackEntity feedback) {
    return feedbackRepository.save(feedback);
  }
}
