package com.qrestor.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.UUID;

@Data
@FieldNameConstants
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractPublicDTO extends BasicDTO {

    private UUID publicId;
}
