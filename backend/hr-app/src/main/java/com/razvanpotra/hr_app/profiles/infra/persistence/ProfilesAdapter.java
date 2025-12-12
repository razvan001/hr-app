package com.razvanpotra.hr_app.profiles.infra.persistence;

import com.razvanpotra.hr_app.exceptions.UserNotFoundException;
import com.razvanpotra.hr_app.profiles.application.ports.ProfilesPort;
import com.razvanpotra.hr_app.profiles.infra.persistence.entities.UserEntity;
import com.razvanpotra.hr_app.profiles.infra.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ProfilesAdapter implements ProfilesPort {

  private final UserRepository userRepository;

  @Override
  public Page<UserEntity> getProfiles(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  @Override
  public UserEntity getProfileByUserName(String userName) {
    return userRepository.findByUserName(userName)
        .orElseThrow(() -> new UserNotFoundException(String.format("User with username %s not found", userName)));
  }

  @Override
  public UserEntity getProfileById(String id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", id)));
  }

  @Override
  public UserEntity saveProfile(UserEntity user) {
    return userRepository.save(user);
  }
}
