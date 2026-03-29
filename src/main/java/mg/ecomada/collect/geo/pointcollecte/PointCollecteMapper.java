package mg.ecomada.collect.geo.pointcollecte;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PointCollecteMapper {
    @Mapping(source = "adresse.id", target = "adresseId")
    @Mapping(source = "adresse.rue", target = "adresseRue")
    PointCollecteDto toDto(PointCollecte entity);

    @Mapping(target = "adresse", ignore = true)
    PointCollecte toEntity(PointCollecteDto dto);
}
