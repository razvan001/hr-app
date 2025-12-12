package com.razvanpotra.hr_app.profiles.infra.mapper;

import com.razvanpotra.hr_app.model.ProfileDto;
import com.razvanpotra.hr_app.profiles.infra.persistence.entities.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {DateMapper.class, FeedbackMapper.class})
public interface UserMapper {

  @Mapping(target = "feedbacks", ignore = true)
  @Mapping(target = "type", ignore = true)
  UserEntity toEntity(ProfileDto dto);
  ProfileDto toDto(UserEntity entity);
}
