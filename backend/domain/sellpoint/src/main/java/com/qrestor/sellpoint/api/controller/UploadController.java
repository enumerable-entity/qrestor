package com.qrestor.sellpoint.api.controller;

import com.qrestor.sellpoint.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.qrestor.commons.common.Constants.MIN_IO_BUCKET_BACKGROUND;
import static com.qrestor.sellpoint.api.RestEndpoints.UPLOAD;

@RestController
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class UploadController {
    private final FileUploadService fileUploadService;

    @PostMapping(value = UPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        String fileName = fileUploadService.uploadFile(file, MIN_IO_BUCKET_BACKGROUND);
        return new ResponseEntity<>(fileName, HttpStatus.CREATED);
    }
}
