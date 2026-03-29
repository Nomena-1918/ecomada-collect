package mg.ecomada.collect.geo.quartier;

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
@RequestMapping("/api/quartiers")
@RequiredArgsConstructor
@Tag(name = "Quartiers", description = "Gestion des quartiers")
public class QuartierController {
    private final QuartierService service;

    @GetMapping
    public CollectionModel<EntityModel<QuartierDto>> getAll(@RequestParam(required = false) Long communeId) {
        List<QuartierDto> list = communeId != null ? service.findByCommuneId(communeId) : service.findAll();
        List<EntityModel<QuartierDto>> items = list.stream()
                .map(d -> EntityModel.of(d, linkTo(methodOn(QuartierController.class).getById(d.getId())).withSelfRel()))
                .toList();
        return CollectionModel.of(items, linkTo(methodOn(QuartierController.class).getAll(communeId)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<QuartierDto> getById(@PathVariable Long id) {
        return EntityModel.of(service.findById(id),
                linkTo(methodOn(QuartierController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(QuartierController.class).getAll(null)).withRel("quartiers"));
    }

    @PostMapping
    public ResponseEntity<EntityModel<QuartierDto>> create(@RequestBody QuartierDto dto) {
        QuartierDto c = service.create(dto);
        return ResponseEntity.created(linkTo(methodOn(QuartierController.class).getById(c.getId())).toUri())
                .body(EntityModel.of(c, linkTo(methodOn(QuartierController.class).getById(c.getId())).withSelfRel()));
    }

    @PutMapping("/{id}")
    public EntityModel<QuartierDto> update(@PathVariable Long id, @RequestBody QuartierDto dto) {
        return EntityModel.of(service.update(id, dto),
                linkTo(methodOn(QuartierController.class).getById(id)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
