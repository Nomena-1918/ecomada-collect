package mg.ecomada.collect.geo.adresse;

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
@RequestMapping("/api/adresses")
@RequiredArgsConstructor
@Tag(name = "Adresses", description = "Gestion des adresses")
public class AdresseController {
    private final AdresseService service;

    @GetMapping
    public CollectionModel<EntityModel<AdresseDto>> getAll() {
        List<EntityModel<AdresseDto>> items = service.findAll().stream()
                .map(d -> EntityModel.of(d, linkTo(methodOn(AdresseController.class).getById(d.getId())).withSelfRel()))
                .toList();
        return CollectionModel.of(items, linkTo(methodOn(AdresseController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<AdresseDto> getById(@PathVariable Long id) {
        return EntityModel.of(service.findById(id),
                linkTo(methodOn(AdresseController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(AdresseController.class).getAll()).withRel("adresses"));
    }

    @PostMapping
    public ResponseEntity<EntityModel<AdresseDto>> create(@RequestBody AdresseDto dto) {
        AdresseDto c = service.create(dto);
        return ResponseEntity.created(linkTo(methodOn(AdresseController.class).getById(c.getId())).toUri())
                .body(EntityModel.of(c, linkTo(methodOn(AdresseController.class).getById(c.getId())).withSelfRel()));
    }

    @PutMapping("/{id}")
    public EntityModel<AdresseDto> update(@PathVariable Long id, @RequestBody AdresseDto dto) {
        return EntityModel.of(service.update(id, dto),
                linkTo(methodOn(AdresseController.class).getById(id)).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
