package mg.ecomada.collect.geo.commune;

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
@RequestMapping("/api/communes")
@RequiredArgsConstructor
@Tag(name = "Communes", description = "Gestion des communes")
public class CommuneController {
    private final CommuneService service;

    @GetMapping
    public CollectionModel<EntityModel<CommuneDto>> getAll(@RequestParam(required = false) Long villeId) {
        List<CommuneDto> list = villeId != null ? service.findByVilleId(villeId) : service.findAll();
        List<EntityModel<CommuneDto>> items = list.stream()
                .map(d -> EntityModel.of(d, linkTo(methodOn(CommuneController.class).getById(d.getId())).withSelfRel()))
                .toList();
        return CollectionModel.of(items, linkTo(methodOn(CommuneController.class).getAll(villeId)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<CommuneDto> getById(@PathVariable Long id) {
        return EntityModel.of(service.findById(id),
                linkTo(methodOn(CommuneController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(CommuneController.class).getAll(null)).withRel("communes"));
    }

    @PostMapping
    public ResponseEntity<EntityModel<CommuneDto>> create(@RequestBody CommuneDto dto) {
        CommuneDto c = service.create(dto);
        return ResponseEntity.created(linkTo(methodOn(CommuneController.class).getById(c.getId())).toUri())
                .body(EntityModel.of(c, linkTo(methodOn(CommuneController.class).getById(c.getId())).withSelfRel()));
    }

    @PutMapping("/{id}")
    public EntityModel<CommuneDto> update(@PathVariable Long id, @RequestBody CommuneDto dto) {
        return EntityModel.of(service.update(id, dto),
                linkTo(methodOn(CommuneController.class).getById(id)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
