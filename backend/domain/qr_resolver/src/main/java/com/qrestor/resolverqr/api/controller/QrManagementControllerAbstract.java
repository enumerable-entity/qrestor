package com.qrestor.resolverqr.api.controller;

import com.qrestor.commons.AbstractCrudController;
import com.qrestor.resolverqr.api.dto.QrCodeMappingDTO;
import com.qrestor.resolverqr.service.QrManagementService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.qrestor.resolverqr.api.RestEndpoints.MANAGEMENT;

@RestController
@PreAuthorize("hasRole('RESTAURATEUR')")
@RequestMapping(MANAGEMENT)
public class QrManagementControllerAbstract extends AbstractCrudController<QrCodeMappingDTO> {
    public QrManagementControllerAbstract(QrManagementService crudService) {
        super(crudService);
    }
}
