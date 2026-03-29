package mg.ecomada.collect.recycleur;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/recycleurs")
@RequiredArgsConstructor
@Tag(name = "Recycleurs", description = "Gestion des recycleurs")
public class RecycleurController {
    private final RecycleurService service;

    @GetMapping
    public CollectionModel<EntityModel<RecycleurDto>> getAll(@RequestParam(required = false) Boolean actif) {
        List<RecycleurDto> list = actif != null ? service.findByActif(actif) : service.findAll();
        List<EntityModel<RecycleurDto>> items = list.stream()
                .map(d -> EntityModel.of(d, linkTo(methodOn(RecycleurController.class).getById(d.getId())).withSelfRel()))
                .toList();
        return CollectionModel.of(items, linkTo(methodOn(RecycleurController.class).getAll(actif)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<RecycleurDto> getById(@PathVariable Long id) {
        return EntityModel.of(service.findById(id),
                linkTo(methodOn(RecycleurController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(RecycleurController.class).getPerformance(id)).withRel("performance"));
    }

    @GetMapping("/{id}/performance")
    public ResponseEntity<Map<String, Object>> getPerformance(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPerformance(id));
    }

    @PostMapping
    public ResponseEntity<EntityModel<RecycleurDto>> create(@RequestBody RecycleurDto dto) {
        RecycleurDto c = service.create(dto);
        return ResponseEntity.created(linkTo(methodOn(RecycleurController.class).getById(c.getId())).toUri())
                .body(EntityModel.of(c, linkTo(methodOn(RecycleurController.class).getById(c.getId())).withSelfRel()));
    }

    @PutMapping("/{id}")
    public EntityModel<RecycleurDto> update(@PathVariable Long id, @RequestBody RecycleurDto dto) {
        return EntityModel.of(service.update(id, dto),
                linkTo(methodOn(RecycleurController.class).getById(id)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
