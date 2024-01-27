package com.qrestor.commons;

import com.qrestor.commons.dto.AbstractPublicDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class CrudController<D extends AbstractPublicDTO> {

    private final CrudService<D> crudService;

    @GetMapping
    public ResponseEntity<List<D>> findAll(@PageableDefault(size = 30) Pageable pageable) {
        return ResponseEntity.ok(crudService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(crudService.findById(id));
    }

    @PostMapping
    public ResponseEntity<D> create(@RequestBody D dto) {
        return new ResponseEntity<>(crudService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> update(@PathVariable UUID id, @RequestBody D dto) {
        dto.setPublicId(id);
        return ResponseEntity.ok(crudService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        crudService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
