package mg.ecomada.collect.geo.ville;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VilleMapper {
    VilleDto toDto(Ville entity);

    Ville toEntity(VilleDto dto);
}
