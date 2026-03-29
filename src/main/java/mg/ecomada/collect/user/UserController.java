package mg.ecomada.collect.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mg.ecomada.collect.recompense.RecompenseService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Utilisateurs", description = "Gestion des utilisateurs")
public class UserController {
    private final UserService service;
    private final RecompenseService recompenseService;

    @GetMapping
    public CollectionModel<EntityModel<UserDto>> getAll() {
        List<EntityModel<UserDto>> items = service.findAll().stream()
                .map(d -> EntityModel.of(d, linkTo(methodOn(UserController.class).getById(d.getId())).withSelfRel()))
                .toList();
        return CollectionModel.of(items, linkTo(methodOn(UserController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<UserDto> getById(@PathVariable Long id) {
        return EntityModel.of(service.findById(id),
                linkTo(methodOn(UserController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getImpact(id)).withRel("impact"));
    }

    @GetMapping("/{id}/impact")
    public ResponseEntity<Map<String, Object>> getImpact(@PathVariable Long id) {
        return ResponseEntity.ok(service.getImpact(id));
    }

    @PostMapping("/{userId}/recompenses/{recompenseId}")
    public ResponseEntity<Void> attribuerRecompense(@PathVariable Long userId, @PathVariable Long recompenseId) {
        recompenseService.attribuer(userId, recompenseId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public EntityModel<UserDto> update(@PathVariable Long id, @RequestBody UserDto dto) {
        return EntityModel.of(service.update(id, dto),
                linkTo(methodOn(UserController.class).getById(id)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
