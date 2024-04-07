package com.qrestor.auth.user.mapper;

import com.qrestor.auth.user.entity.SystemUserSettings;
import com.qrestor.models.dto.auth.UserSettingsDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserSettingsMapper {
    UserSettingsDTO toDto(SystemUserSettings entity);

    SystemUserSettings toEntity(UserSettingsDTO dto);

}
