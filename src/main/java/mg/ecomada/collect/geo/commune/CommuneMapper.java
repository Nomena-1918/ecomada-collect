package mg.ecomada.collect.geo.commune;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommuneMapper {
    @Mapping(source = "ville.id", target = "villeId")
    @Mapping(source = "ville.nom", target = "villeNom")
    CommuneDto toDto(Commune entity);

    @Mapping(target = "ville", ignore = true)
    Commune toEntity(CommuneDto dto);
}
