package com.razvanpotra.hr_app.profiles.application.ports;

import com.razvanpotra.hr_app.profiles.infra.persistence.entities.FeedbackEntity;

public interface FeedbackPort {

  FeedbackEntity createFeedback(FeedbackEntity feedback);
}
