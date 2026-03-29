package mg.ecomada.collect.depot;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/depots")
@RequiredArgsConstructor
@Tag(name = "Depots", description = "Gestion des depots de dechets")
public class DepotController {
    private final DepotService service;

    @GetMapping
    public Page<DepotDto> getAll(
            @RequestParam(required = false) Long villeId,
            @RequestParam(required = false) Long statusId,
            @RequestParam(required = false) Long typeDechetId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateMin,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateMax,
            Pageable pageable) {
        return service.findAll(villeId, statusId, typeDechetId, dateMin, dateMax, pageable);
    }

    @GetMapping("/{id}")
    public EntityModel<DepotDto> getById(@PathVariable Long id) {
        DepotDto dto = service.findById(id);
        return EntityModel.of(dto,
                linkTo(methodOn(DepotController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(DepotController.class).getAll(null, null, null, null, null, Pageable.unpaged())).withRel("depots"));
    }

    @PostMapping
    public ResponseEntity<EntityModel<DepotDto>> create(@RequestBody DepotDto dto) {
        DepotDto created = service.create(dto);
        return ResponseEntity.created(linkTo(methodOn(DepotController.class).getById(created.getId())).toUri())
                .body(EntityModel.of(created, linkTo(methodOn(DepotController.class).getById(created.getId())).withSelfRel()));
    }

    @PatchMapping("/{id}/status")
    public EntityModel<DepotDto> updateStatus(@PathVariable Long id,
                                              @RequestParam Long statusId,
                                              @RequestParam(required = false) Long collecteurId,
                                              @RequestParam(required = false) Long recycleurId) {
        return EntityModel.of(service.updateStatus(id, statusId, collecteurId, recycleurId),
                linkTo(methodOn(DepotController.class).getById(id)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
