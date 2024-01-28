package com.qrestor.commons.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.UUID;

@Data
@FieldNameConstants
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractPublicDTO extends BasicDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID publicId;
}
