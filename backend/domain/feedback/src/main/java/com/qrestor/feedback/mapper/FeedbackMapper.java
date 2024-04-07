package com.qrestor.feedback.mapper;

import com.qrestor.feedback.api.dto.FeedbackDTO;
import com.qrestor.feedback.entity.FeedbackEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface FeedbackMapper {


    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    FeedbackEntity toEntity(FeedbackDTO feedback);

    List<FeedbackEntity> toEntities(List<FeedbackDTO> feedback);


    FeedbackDTO toDTO(FeedbackEntity feedback);

    List<FeedbackDTO> toDTOs(List<FeedbackEntity> feedback);
}
