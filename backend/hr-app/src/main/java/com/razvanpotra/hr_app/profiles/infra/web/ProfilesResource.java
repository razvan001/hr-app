package com.razvanpotra.hr_app.profiles.infra.web;

import com.razvanpotra.hr_app.controller.ProfileApi;
import com.razvanpotra.hr_app.model.ProfileDto;
import com.razvanpotra.hr_app.model.ProfilesDtoResponse;
import com.razvanpotra.hr_app.profiles.application.service.ProfilesService;
import com.razvanpotra.hr_app.profiles.infra.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProfilesResource implements ProfileApi {

  private final ProfilesService profilesService;
  private final UserMapper userMapper;

  @Override
  public ResponseEntity<ProfilesDtoResponse> getProfiles(
      Integer page,
      Integer size) {
    log.info("Getting profiles");
    var profiles = profilesService.getProfiles(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")))
        .map(userMapper::toDto);
    var response = new ProfilesDtoResponse();
    response.setTotalElements((int) profiles.getTotalElements());
    response.setTotalPages(profiles.getTotalPages());
    response.setContent(profiles.getContent());
    response.setPage(page);
    response.setSize(size);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<ProfileDto> getProfileByUserName(String userName) {
    log.info("Getting profile by userName: {}", userName);
    return ResponseEntity.ok(userMapper.toDto(profilesService.getProfileByUserName(userName)));
  }

  @Override
  public ResponseEntity<ProfileDto> updateProfileByUserName(String userName, ProfileDto profileDto) {
    log.info("Updating profile by userName: {}", userName);
    return ResponseEntity.ok(
        userMapper.toDto(profilesService.updateProfile(userName, userMapper.toEntity(profileDto))));
  }
}
