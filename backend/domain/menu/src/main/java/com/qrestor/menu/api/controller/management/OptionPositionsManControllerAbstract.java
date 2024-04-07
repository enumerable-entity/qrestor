package com.qrestor.menu.api.controller.management;

import com.qrestor.commons.AbstractCrudController;
import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.service.impl.MenuItemOptionPositionsServiceImpl;
import com.qrestor.models.dto.menu.MenuItemOptionPositionDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RestEndpoints.MENU_ITEM_OPTION_POSITIONS_MANAGEMENT)
@PreAuthorize("hasRole('RESTAURATEUR')")
public class OptionPositionsManControllerAbstract extends AbstractCrudController<MenuItemOptionPositionDTO> {

    MenuItemOptionPositionsServiceImpl positionsService;

    public OptionPositionsManControllerAbstract(MenuItemOptionPositionsServiceImpl crudService) {
        super(crudService);
        this.positionsService = crudService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MenuItemOptionPositionDTO>> findAllByMenuItemId(@PageableDefault(size = 30) Pageable pageable,
                                                                               @RequestParam(required = false) UUID optionId) {
        return ResponseEntity.ok(positionsService.findAllByOptionId(pageable, optionId, false));
    }
}
