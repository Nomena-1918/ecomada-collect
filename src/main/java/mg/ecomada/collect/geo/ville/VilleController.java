package mg.ecomada.collect.geo.ville;

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
@RequestMapping("/api/villes")
@RequiredArgsConstructor
@Tag(name = "Villes", description = "Gestion des villes")
public class VilleController {
    private final VilleService service;

    @GetMapping
    public CollectionModel<EntityModel<VilleDto>> getAll() {
        List<EntityModel<VilleDto>> items = service.findAll().stream()
                .map(d -> EntityModel.of(d, linkTo(methodOn(VilleController.class).getById(d.getId())).withSelfRel()))
                .toList();
        return CollectionModel.of(items, linkTo(methodOn(VilleController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<VilleDto> getById(@PathVariable Long id) {
        VilleDto dto = service.findById(id);
        return EntityModel.of(dto,
                linkTo(methodOn(VilleController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(VilleController.class).getAll()).withRel("villes"));
    }

    @PostMapping
    public ResponseEntity<EntityModel<VilleDto>> create(@RequestBody VilleDto dto) {
        VilleDto created = service.create(dto);
        return ResponseEntity.created(linkTo(methodOn(VilleController.class).getById(created.getId())).toUri())
                .body(EntityModel.of(created, linkTo(methodOn(VilleController.class).getById(created.getId())).withSelfRel()));
    }

    @PutMapping("/{id}")
    public EntityModel<VilleDto> update(@PathVariable Long id, @RequestBody VilleDto dto) {
        return EntityModel.of(service.update(id, dto),
                linkTo(methodOn(VilleController.class).getById(id)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
