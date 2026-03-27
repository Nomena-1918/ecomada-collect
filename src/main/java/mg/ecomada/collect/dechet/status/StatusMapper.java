package mg.ecomada.collect.dechet.status;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    StatusDto toDto(Status entity);

    Status toEntity(StatusDto dto);
}
