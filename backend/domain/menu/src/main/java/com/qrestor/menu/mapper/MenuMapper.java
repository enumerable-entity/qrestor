package com.qrestor.menu.mapper;

import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.menu.api.dto.MenuDTO;
import com.qrestor.menu.entity.MenuEntity;
import org.mapstruct.Mapper;

@Mapper
public interface MenuMapper extends CrudMapper<MenuDTO, MenuEntity> {
}
