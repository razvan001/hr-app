package com.razvanpotra.hr_app.profiles.infra.web;

import com.razvanpotra.hr_app.controller.FeedbackApi;
import com.razvanpotra.hr_app.model.FeedbackCreateDto;
import com.razvanpotra.hr_app.model.FeedbackDto;
import com.razvanpotra.hr_app.profiles.application.service.FeedbackService;
import com.razvanpotra.hr_app.profiles.infra.mapper.FeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedbackResource implements FeedbackApi {

  private final FeedbackService feedbackService;
  private final FeedbackMapper feedbackMapper;

  @Override
  public ResponseEntity<FeedbackDto> createFeedback(FeedbackCreateDto feedbackCreateDto) {
    var feedback = feedbackService.createFeedback(
        feedbackCreateDto.getAuthor(),
        feedbackCreateDto.getText(),
        feedbackCreateDto.getProfileUsername()
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(feedbackMapper.toDto(feedback));
  }

}
