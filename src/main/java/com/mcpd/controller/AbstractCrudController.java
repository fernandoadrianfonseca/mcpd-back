package com.mcpd.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudController<T> {

    protected abstract List<T> getAllEntities();

    protected abstract Optional<T> getEntityById(Integer id);

    protected abstract T createOrUpdateEntity(T entity);

    protected abstract void deleteEntity(Integer id);

    @GetMapping
    public List<T> getAll() {
        return getAllEntities();
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable Integer id) {
        Optional<T> entity = getEntityById(id);
        return entity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public T create(@RequestBody T entity) {
        return createOrUpdateEntity(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable Integer id, @RequestBody T entity) {
        if (!getEntityById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        setEntityId(entity, id);
        return ResponseEntity.ok(createOrUpdateEntity(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!getEntityById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        deleteEntity(id);
        return ResponseEntity.noContent().build();
    }

    protected abstract void setEntityId(T entity, Integer id);
}
