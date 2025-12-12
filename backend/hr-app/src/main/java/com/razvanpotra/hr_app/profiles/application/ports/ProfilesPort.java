package com.razvanpotra.hr_app.profiles.application.ports;

import com.razvanpotra.hr_app.profiles.infra.persistence.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfilesPort {

  Page<UserEntity> getProfiles(Pageable pageable);
  UserEntity getProfileByUserName(String userName);
  UserEntity getProfileById(String id);
  UserEntity saveProfile(UserEntity user);
}
