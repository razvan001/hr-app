package com.razvanpotra.hr_app.profiles.infra.mapper;

import com.razvanpotra.hr_app.model.FeedbackDto;
import com.razvanpotra.hr_app.profiles.infra.persistence.entities.FeedbackEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = DateMapper.class)
public interface FeedbackMapper {

  FeedbackDto toDto(FeedbackEntity entity);


}
