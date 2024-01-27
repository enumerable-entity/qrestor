package com.qrestor.resolverqr.service;

import com.qrestor.commons.AbstractCrudService;
import com.qrestor.commons.CrudService;
import com.qrestor.commons.PublicRepository;
import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.resolverqr.api.dto.QrCodeMappingDTO;
import com.qrestor.resolverqr.entity.QrCodeMappingEntity;
import org.springframework.stereotype.Service;

@Service
public class QrManagementService extends AbstractCrudService<QrCodeMappingDTO, QrCodeMappingEntity>
        implements CrudService<QrCodeMappingDTO> {

    public QrManagementService(CrudMapper<QrCodeMappingDTO, QrCodeMappingEntity> mapper, PublicRepository<QrCodeMappingEntity, Long> repository) {
        super(mapper, repository);
    }

}
