package mg.ecomada.collect.geo.adresse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdresseMapper {
    @Mapping(source = "quartier.id", target = "quartierId")
    @Mapping(source = "quartier.nom", target = "quartierNom")
    AdresseDto toDto(Adresse entity);

    @Mapping(target = "quartier", ignore = true)
    Adresse toEntity(AdresseDto dto);
}
