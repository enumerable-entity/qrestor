package com.qrestor.menu.api.controller;

import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.api.dto.list.MenuListDTO;
import com.qrestor.menu.service.MenuPublicService;
import com.qrestor.models.dto.menu.MenuItemOptionDTO;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RestEndpoints.MENU)
@RequiredArgsConstructor
public class MenuController {

    private final MenuPublicService menuPublicService;

    @GetMapping("/{restId}")
    public ResponseEntity<List<MenuListDTO>> getActiveMenuForRestaurant(@PathVariable UUID restId) {
        return new ResponseEntity<>(menuPublicService.getActiveMenu(restId), HttpStatus.OK);
    }

    @GetMapping("/{menuId}/{menuItemId}")
    public ResponseEntity<List<MenuItemOptionDTO>> getMenuItemOptions(@PathVariable UUID menuId, @PathVariable UUID menuItemId) {
        return new ResponseEntity<>(menuPublicService.getMenuItemOptions(menuId, menuItemId), HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file)
    {

        String extension = "";
        int lastIndex = file.getOriginalFilename().lastIndexOf(".");

        if (lastIndex > 0) {
            extension = file.getOriginalFilename().substring(lastIndex + 1);
        }

        var savedFileName = UUID.randomUUID().toString().concat(".").concat(extension);
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("https://127.0.0.1:9000")
                        .credentials("qrestor123", "qrestor123")
                        .build();

        try {
            minioClient.putObject(PutObjectArgs
                    .builder()
                    .bucket("menu-items-pics")
                    .object(savedFileName)
                    .stream(file.getInputStream(), file.getSize(), -1).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(savedFileName, HttpStatus.CREATED);
    }

}
