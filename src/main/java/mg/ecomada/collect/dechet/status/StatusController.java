package mg.ecomada.collect.dechet.status;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/status")
@RequiredArgsConstructor
@Tag(name = "Statuts", description = "Gestion des statuts")
public class StatusController {
    private final StatusService service;

    @GetMapping
    public CollectionModel<EntityModel<StatusDto>> getAll() {
        List<EntityModel<StatusDto>> items = service.findAll().stream()
                .map(d -> EntityModel.of(d, linkTo(methodOn(StatusController.class).getById(d.getId())).withSelfRel()))
                .toList();
        return CollectionModel.of(items, linkTo(methodOn(StatusController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<StatusDto> getById(@PathVariable Long id) {
        StatusDto dto = service.findById(id);
        return EntityModel.of(dto,
                linkTo(methodOn(StatusController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(StatusController.class).getAll()).withRel("status"));
    }

    @PostMapping
    public ResponseEntity<EntityModel<StatusDto>> create(@RequestBody StatusDto dto) {
        StatusDto created = service.create(dto);
        return ResponseEntity.created(linkTo(methodOn(StatusController.class).getById(created.getId())).toUri())
                .body(EntityModel.of(created, linkTo(methodOn(StatusController.class).getById(created.getId())).withSelfRel()));
    }

    @PutMapping("/{id}")
    public EntityModel<StatusDto> update(@PathVariable Long id, @RequestBody StatusDto dto) {
        return EntityModel.of(service.update(id, dto),
                linkTo(methodOn(StatusController.class).getById(id)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
