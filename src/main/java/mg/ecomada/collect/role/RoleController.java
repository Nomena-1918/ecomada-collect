package mg.ecomada.collect.role;

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
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Gestion des roles")
public class RoleController {
    private final RoleService service;

    @GetMapping
    public CollectionModel<EntityModel<RoleDto>> getAll() {
        List<EntityModel<RoleDto>> items = service.findAll().stream()
                .map(d -> EntityModel.of(d, linkTo(methodOn(RoleController.class).getById(d.getId())).withSelfRel()))
                .toList();
        return CollectionModel.of(items, linkTo(methodOn(RoleController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<RoleDto> getById(@PathVariable Long id) {
        RoleDto dto = service.findById(id);
        return EntityModel.of(dto,
                linkTo(methodOn(RoleController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(RoleController.class).getAll()).withRel("roles"));
    }

    @PostMapping
    public ResponseEntity<EntityModel<RoleDto>> create(@RequestBody RoleDto dto) {
        RoleDto created = service.create(dto);
        return ResponseEntity.created(linkTo(methodOn(RoleController.class).getById(created.getId())).toUri())
                .body(EntityModel.of(created, linkTo(methodOn(RoleController.class).getById(created.getId())).withSelfRel()));
    }

    @PutMapping("/{id}")
    public EntityModel<RoleDto> update(@PathVariable Long id, @RequestBody RoleDto dto) {
        return EntityModel.of(service.update(id, dto),
                linkTo(methodOn(RoleController.class).getById(id)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
