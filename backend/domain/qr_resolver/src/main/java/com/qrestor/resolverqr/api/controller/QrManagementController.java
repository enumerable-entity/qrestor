package com.qrestor.resolverqr.api.controller;

import com.qrestor.commons.CrudController;
import com.qrestor.resolverqr.api.dto.QrCodeMappingDTO;
import com.qrestor.commons.CrudService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.qrestor.resolverqr.api.RestEndpoints.MANAGEMENT;

@RestController
@PreAuthorize("hasRole('RESTAURATEUR')")
@RequestMapping(MANAGEMENT)
public class QrManagementController extends CrudController<QrCodeMappingDTO> {
    public QrManagementController(CrudService<QrCodeMappingDTO> crudService) {
        super(crudService);
    }
}
