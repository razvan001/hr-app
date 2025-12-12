package com.razvanpotra.hr_app.profiles.application.service;

import com.razvanpotra.hr_app.profiles.application.ports.ProfilesPort;
import com.razvanpotra.hr_app.profiles.infra.persistence.entities.FeedbackEntity;
import com.razvanpotra.hr_app.profiles.infra.persistence.entities.UserEntity;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfilesService {

  private final ProfilesPort profilesPort;

  public Page<UserEntity> getProfiles(Pageable pageable) {

    return profilesPort.getProfiles(pageable);
  }

  public UserEntity getProfileByUserName(String userName) {
    var profile = profilesPort.getProfileByUserName(userName);

    var feedbacks = profile.getFeedbacks();
    if (feedbacks != null) {
      feedbacks.sort(Comparator.comparing(FeedbackEntity::getDate).reversed());
    }

    return profile;
  }

  public UserEntity updateProfile(String userName, UserEntity updatedUser) {
    var existingProfile = profilesPort.getProfileByUserName(userName);
    var updatedProfile = existingProfile.toBuilder()
        .name(updatedUser.getName())
        .userName(updatedUser.getUserName())
        .salary(updatedUser.getSalary())
        .department(updatedUser.getDepartment())
        .email(updatedUser.getEmail())
        .position(updatedUser.getPosition())
        .build();

    return profilesPort.saveProfile(updatedProfile);
  }
}
