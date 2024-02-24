package com.qrestor.auth.user.mapper;

import com.qrestor.models.dto.auth.UserSettingsDTO;
import com.qrestor.auth.user.entity.SystemUserSettings;
import org.mapstruct.Mapper;

@Mapper
public interface UserSettingsMapper {
    UserSettingsDTO toDto(SystemUserSettings entity);

    SystemUserSettings toEntity(UserSettingsDTO dto);

}
