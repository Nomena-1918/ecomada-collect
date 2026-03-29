package mg.ecomada.collect.geo.pointcollecte;

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
@RequestMapping("/api/points-collecte")
@RequiredArgsConstructor
@Tag(name = "Points de collecte", description = "Gestion des points de collecte")
public class PointCollecteController {
    private final PointCollecteService service;

    @GetMapping
    public CollectionModel<EntityModel<PointCollecteDto>> getAll(@RequestParam(required = false) Boolean actif) {
        List<PointCollecteDto> list = actif != null ? service.findByActif(actif) : service.findAll();
        List<EntityModel<PointCollecteDto>> items = list.stream()
                .map(d -> EntityModel.of(d, linkTo(methodOn(PointCollecteController.class).getById(d.getId())).withSelfRel()))
                .toList();
        return CollectionModel.of(items, linkTo(methodOn(PointCollecteController.class).getAll(actif)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<PointCollecteDto> getById(@PathVariable Long id) {
        return EntityModel.of(service.findById(id),
                linkTo(methodOn(PointCollecteController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(PointCollecteController.class).getAll(null)).withRel("points-collecte"));
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<Map<String, Object>> getStats(@PathVariable Long id) {
        return ResponseEntity.ok(service.getStats(id));
    }

    @PostMapping
    public ResponseEntity<EntityModel<PointCollecteDto>> create(@RequestBody PointCollecteDto dto) {
        PointCollecteDto c = service.create(dto);
        return ResponseEntity.created(linkTo(methodOn(PointCollecteController.class).getById(c.getId())).toUri())
                .body(EntityModel.of(c, linkTo(methodOn(PointCollecteController.class).getById(c.getId())).withSelfRel()));
    }

    @PutMapping("/{id}")
    public EntityModel<PointCollecteDto> update(@PathVariable Long id, @RequestBody PointCollecteDto dto) {
        return EntityModel.of(service.update(id, dto),
                linkTo(methodOn(PointCollecteController.class).getById(id)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
