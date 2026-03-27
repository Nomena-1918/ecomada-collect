package mg.ecomada.collect.dechet.typedechet;

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
@RequestMapping("/api/types-dechets")
@RequiredArgsConstructor
@Tag(name = "Types de dechets", description = "Gestion des types de dechets")
public class TypeDechetController {
    private final TypeDechetService service;

    @GetMapping
    public CollectionModel<EntityModel<TypeDechetDto>> getAll() {
        List<EntityModel<TypeDechetDto>> items = service.findAll().stream()
                .map(d -> EntityModel.of(d, linkTo(methodOn(TypeDechetController.class).getById(d.getId())).withSelfRel()))
                .toList();
        return CollectionModel.of(items, linkTo(methodOn(TypeDechetController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<TypeDechetDto> getById(@PathVariable Long id) {
        TypeDechetDto dto = service.findById(id);
        return EntityModel.of(dto,
                linkTo(methodOn(TypeDechetController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(TypeDechetController.class).getAll()).withRel("types-dechets"));
    }

    @PostMapping
    public ResponseEntity<EntityModel<TypeDechetDto>> create(@RequestBody TypeDechetDto dto) {
        TypeDechetDto created = service.create(dto);
        return ResponseEntity.created(linkTo(methodOn(TypeDechetController.class).getById(created.getId())).toUri())
                .body(EntityModel.of(created, linkTo(methodOn(TypeDechetController.class).getById(created.getId())).withSelfRel()));
    }

    @PutMapping("/{id}")
    public EntityModel<TypeDechetDto> update(@PathVariable Long id, @RequestBody TypeDechetDto dto) {
        return EntityModel.of(service.update(id, dto),
                linkTo(methodOn(TypeDechetController.class).getById(id)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
