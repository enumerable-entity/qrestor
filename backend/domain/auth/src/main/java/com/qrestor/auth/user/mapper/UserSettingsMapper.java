package com.qrestor.auth.user.mapper;

import com.qrestor.auth.user.dto.AddressDTO;
import com.qrestor.auth.user.dto.UserSettingsDTO;
import com.qrestor.auth.user.entity.AddressEntity;
import com.qrestor.auth.user.entity.SystemUserSettings;
import org.mapstruct.Mapper;

@Mapper
public interface UserSettingsMapper {
    UserSettingsDTO toDto(SystemUserSettings entity);

}
