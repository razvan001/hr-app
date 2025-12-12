package com.razvanpotra.hr_app.profiles.infra.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DateMapper {

  default OffsetDateTime map(LocalDateTime value) {
    return value == null ? null : value.atOffset(ZoneOffset.UTC);
  }
}
