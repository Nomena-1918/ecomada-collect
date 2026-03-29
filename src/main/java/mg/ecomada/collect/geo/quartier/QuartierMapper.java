package mg.ecomada.collect.geo.quartier;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuartierMapper {
    @Mapping(source = "commune.id", target = "communeId")
    @Mapping(source = "commune.nom", target = "communeNom")
    QuartierDto toDto(Quartier entity);

    @Mapping(target = "commune", ignore = true)
    Quartier toEntity(QuartierDto dto);
}
