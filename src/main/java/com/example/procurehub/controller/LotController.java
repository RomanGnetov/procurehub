package com.example.procurehub.controller;

import com.example.jooq.tables.pojos.Lot;
import com.example.procurehub.service.LotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lots")
public class LotController {

    private final LotService lotService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @GetMapping
    public List<Lot> getAll() {
        return lotService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lot> getById(@PathVariable Integer id) {
        return lotService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Lot> create(@RequestBody Lot lot) {
        Lot created = lotService.save(lot);
        URI location = URI.create(String.format("/api/lots/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lot> update(@PathVariable Integer id, @RequestBody Lot lot) {
        Optional<Lot> updated = lotService.update(id, lot);
        return updated
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        boolean deleted = lotService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}