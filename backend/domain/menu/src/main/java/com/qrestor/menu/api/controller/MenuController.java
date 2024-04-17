package com.qrestor.menu.api.controller;

import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.api.dto.list.MenuListDTO;
import com.qrestor.menu.service.FileUploadService;
import com.qrestor.menu.service.MenuPublicService;
import com.qrestor.models.dto.menu.MenuItemOptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.qrestor.commons.common.Constants.MIN_IO_BUCKET_MENU_ITEMS;

@RestController
@RequestMapping(RestEndpoints.MENU)
@RequiredArgsConstructor
public class MenuController {

    private final MenuPublicService menuPublicService;
    private final FileUploadService fileUploadService;

    @GetMapping("/{restId}")
    public ResponseEntity<List<MenuListDTO>> getActiveMenuForRestaurant(@PathVariable UUID restId) {
        return new ResponseEntity<>(menuPublicService.getActiveMenu(restId), HttpStatus.OK);
    }

    @GetMapping("/{menuId}/{menuItemId}")
    public ResponseEntity<List<MenuItemOptionDTO>> getMenuItemOptions(@PathVariable UUID menuId,
                                                                      @PathVariable UUID menuItemId) {
        return new ResponseEntity<>(menuPublicService.getMenuItemOptions(menuId, menuItemId), HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        String fileName = fileUploadService.uploadFile(file, MIN_IO_BUCKET_MENU_ITEMS);
        return new ResponseEntity<>(fileName, HttpStatus.CREATED);
    }

}
